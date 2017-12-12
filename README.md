#EDIT 18.07.2017

gpw.pl had a site refactor when i tried to save quotation in local file but they changed urls. Update is coming.. Sorry

# restful-profit
Stock market simulator - RESTful application
- Spring Boot 1.4.0
- Gradle
- Hibernate 5.2.2
- Hsqldb/postgresql
- REST
- Angularjs

# Long starting?
The application runs slowly because of a one method  - addShares in [GpwSharesDownloader](src/main/java/pl/pvkk/profit/gpw/GpwSharesDownloader.java) with @PostConstruct annotation. It gets content from other site [gpw.pl](https://www.gpw.pl/), downloads quotations at stock exchange and saves them in HSQL database.

# About
Default user is:
- login: "login"
- password: "password"

If you want to register a new one you have to set your email address because in registration process you get a confirmation mail and it's needed to set any server email address
![ss](/src/main/webapp/resources/smtp configuration.png?raw=true) (application.properties in resources)

You may also change accessibility for other apps in your gmail account. Just turn it off.
https://www.google.com/settings/security/lesssecureapps

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
