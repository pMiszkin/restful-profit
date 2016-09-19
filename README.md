# restful-profit
Stock market simulator - RESTful application
- Spring Boot 1.4.0
- Gradle
- Hibernate 5.2.2
- Hsqldb/postgresql


Here you have mapped URLs. You can check olso /mappedurls for current list. Each adress works on **localhost:8080**
```
"[/mappedurls] -> [GET] PARAMS: ",
"[/transfer/sell] -> [POST] PARAMS: @RequestParam String shareName, @RequestParam int shareNumber",
"[/transfer/buy] -> [POST] PARAMS: @RequestParam String shareName, @RequestParam int shareNumber",
"[/share/{stockIndex}] -> [GET] PARAMS: @PathVariable String stockIndex",
"[/pocket] -> [GET] PARAMS: ",
"[/user/add] -> [POST] PARAMS: @Valid @RequestBody User user, BindingResult result",
"[/user/print/{userId}] -> [GET] PARAMS: @PathVariable int userId",
```