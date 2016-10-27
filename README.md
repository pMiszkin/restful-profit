# restful-profit
Stock market simulator - RESTful application
- Spring Boot 1.4.0
- Gradle
- Hibernate 5.2.2
- Hsqldb/postgresql
- REST
- Angularjs

# Long starting?
Application starting about 5 minutes through the one method  - addShares in [GpwSharesDownloader](src/main/java/pl/pvkk/profit/gpw/GpwSharesDownloader.java) with @PostConstruct annotation. This gets connect with outside site [gpw.pl](https://www.gpw.pl/) downloads quotations at stock exchange and they are going to save at HSQL database.

# About
Here you have mapped URLs. Each adress works on **localhost:8080**. Also you can check /mappedurls for current list.
```
"[/mappedurls] -> [GET] PARAMS: ",
"(VIEW) [/, /home] -> [GET] PARAMS: ",
"(VIEW) [/login] -> [GET] PARAMS: ",
"[/shares/all] -> [GET] PARAMS: ",
"[/shares/company/{shareShortcut}] -> [GET] PARAMS: @PathVariable String shareShortcut, ",
"[/shares/indices/all] -> [GET] PARAMS: ",
"[/user/add] -> [POST] PARAMS: @Valid @RequestBody User user, BindingResult result, ",
"[/user/print/{username}] -> [GET] PARAMS: @PathVariable String username, ",
"[/pocket/{pocketId}] -> [GET] PARAMS: @PathVariable long pocketId, ",
"[/transfer/buy] -> [POST] PARAMS: @RequestParam String shareShortcut, @RequestParam int shareNumber, ",
"[/transfer/sell] -> [POST] PARAMS: @RequestParam String shareShortcut, @RequestParam int shareNumber, ",
```

![ss](/src/main/webapp/resources/ss.png?raw=true)
