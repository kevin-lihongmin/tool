# tool
一些工具和测试结果

datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: root
        password: root
        url: jdbc:mysql://47.110.90.27:3306/db_alibaba?characterEncoding=utf-8&serverTimezone=UTC
        type: com.alibaba.druid.pool.DruidDataSource
        druid:
        initial-size: 2
        min-idle: 1
        max-active: 50
        max-wait: 60000
        pool-prepared-statements: true
        time-between-eviction-runs-millis: 60000
        min-evictable-idle-time-millis: 300000
        validation-query: select 1
        test-while-idle: true
        test-on-borrow: false
        test-on-return: false
        max-pool-prepared-statement-per-connection-size: 50
        web-stat-filter:
        enabled: true
        url-pattern: /*
        exclusions: /druid/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico
        session-stat-enable: true
        profile-enable: true
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        reset-enable: true
        login-username: root
        login-password: qwer1234
        allow:
        deny:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
    database: mysql
    open-in-view: true
    properties:
      hibernate:
        enable_lazy_load_no_trans: true
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
