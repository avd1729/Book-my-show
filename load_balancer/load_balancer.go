package main

import (
    "io"
    "net/http"
    "time"
    "log"
)

var servers = []string{
    "http://server1:8080",
    "http://server2:8080",
    "http://server3:8080",
}

var currentIndex int = 0

func getServer() string {
    server := servers[currentIndex]
    currentIndex = (currentIndex + 1) % len(servers)
    return server
}

func checkServerHealth(server string) bool {
    client := http.Client{
        Timeout: 2 * time.Second,
    }
    
    // Try multiple endpoints to check health
    endpoints := []string{
        "/status",                // Your custom status endpoint
        "/actuator/health",       // Spring Boot actuator health endpoint
        "/",                      // Root path as fallback
    }
    
    for _, endpoint := range endpoints {
        checkURL := server + endpoint
        log.Printf("Checking health of server: %s", checkURL)
        
        resp, err := client.Get(checkURL)
        if err != nil {
            log.Printf("Error checking server health at %s: %v", checkURL, err)
            continue
        }
        
        defer resp.Body.Close()
        
        if resp.StatusCode < 300 {
            log.Printf("Server %s is healthy (responded with %d)", server, resp.StatusCode)
            return true
        }
        
        log.Printf("Server returned status: %d for endpoint %s", resp.StatusCode, checkURL)
    }
    
    return false
}

func loadBalance(w http.ResponseWriter, r *http.Request) {
    attempts := 0
    maxAttempts := len(servers) * 2
    
    for attempts < maxAttempts {
        curr := getServer()
        if checkServerHealth(curr) {
            // Construct the target URL with the original path and query
            targetURL := curr + r.URL.Path
            if r.URL.RawQuery != "" {
                targetURL += "?" + r.URL.RawQuery
            }
            
            log.Printf("Forwarding request to: %s", targetURL)
            
            // Create a new request with the same method and body
            proxyReq, err := http.NewRequest(r.Method, targetURL, r.Body)
            if err != nil {
                log.Printf("Error creating request: %v", err)
                attempts++
                continue
            }
            
            // Copy the original headers
            for name, values := range r.Header {
                for _, value := range values {
                    proxyReq.Header.Add(name, value)
                }
            }
            
            // Add X-Forwarded headers
            proxyReq.Header.Set("X-Forwarded-For", r.RemoteAddr)
            proxyReq.Header.Set("X-Forwarded-Host", r.Host)
            proxyReq.Header.Set("X-Forwarded-Proto", "http")
            
            // Forward the request to the backend
            client := http.Client{
                Timeout: 5 * time.Second,
            }
            
            resp, err := client.Do(proxyReq)
            if err != nil {
                log.Printf("Error forwarding request: %v", err)
                attempts++
                continue
            }
            
            defer resp.Body.Close()
            
            // Copy response headers back to client
            for name, values := range resp.Header {
                for _, value := range values {
                    w.Header().Add(name, value)
                }
            }
            
            // Forward response to the client
            w.WriteHeader(resp.StatusCode)
            io.Copy(w, resp.Body)
            return
        }
        
        attempts++
    }
    
    http.Error(w, "No healthy backend servers available", http.StatusServiceUnavailable)
}

func main() {
    http.HandleFunc("/", loadBalance)
    log.Println("Load balancer running on :8080")
    err := http.ListenAndServe(":8080", nil)
    if err != nil {
        log.Fatalf("Failed to start server: %v", err)
    }
}