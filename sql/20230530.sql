create table base_user_auth_type
(
    id             int unsigned auto_increment comment '主键id'
        primary key,
    user_auth_type int unsigned                       not null comment '用户验证策略：1：ops用户,2：系统用户模式 ，3：子系统用户模式 ',
    user_api       varchar(200)     default ''        not null comment '获取用户信息接口',
    enable_cache   tinyint unsigned default '0'       not null comment '是否缓存：1 是 ，0否',
    enable_record  tinyint unsigned default '0'       not null comment '是否保存记录：1 是 ，0 否',
    resolve_param  varchar(50)      default ''        not null comment '需要解析的参数,使用逗号分隔',
    record_api     varchar(50)      default ''        not null comment '保存用户信息的接口',
    enable_kickout tinyint unsigned default '0'       not null comment '是否互踢：1 是； 0否',
    cache_expire   int unsigned     default '2592000' null comment '缓存时间,单位/秒',
    create_time    datetime                           not null comment '创建时间',
    update_time    datetime                           not null comment '更新时间'
)
    comment '用户认证策略表' charset = utf8;

create table check_log
(
    id           bigint auto_increment
        primary key,
    instance_id  varchar(20)  not null comment '实例id',
    event_time   datetime     null comment '事件时间',
    event_status varchar(20)  not null comment '事件时间',
    details      text         null comment '事件详情',
    app_name     varchar(20)  null comment '服务名称',
    service_url  varchar(100) null comment '服务地址',
    jstack_msg   text         null comment '线程堆栈信息'
)
    comment '服务检查日志';

create table gateway_client_route
(
    id        int auto_increment
        primary key,
    route_id  int          not null comment '路由id',
    client_id varchar(100) not null comment '客户端id',
    constraint route_id
        unique (route_id, client_id)
);

create table gateway_degrade_rule
(
    id                 int auto_increment
        primary key,
    resource           varchar(30)         not null comment '资源名称，可以是网关中的 route 名称或者用户自定义的API 分组名称',
    count              double              not null comment '降级策略为RT则表示响应时间；降级策略为异常比例则表示异常比例；降级策略为异常数则表示异常数量',
    timeWindow         int    default 0    null comment '降级的时间窗口，在该窗口时间内请求都不能通过',
    grade              double default 0    null comment '降级熔断策略',
    minRequestAmount   int    default 5    null comment '熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断',
    slowRatioThreshold double default 1    null comment '慢调用比例阈值，仅慢调用比例模式有效',
    statIntervalMs     int    default 1000 null comment '统计时长（单位为 ms），如 60*1000 代表分钟级'
);

create table gateway_filter
(
    id          int auto_increment
        primary key,
    type        varchar(30)                             not null,
    name        varchar(50)                             null,
    definition  varchar(255)                            not null,
    isdefault   tinyint(1)  default 0                   null,
    remarks     varchar(255)                            null,
    create_by   varchar(50)                             null,
    create_date datetime                                null,
    update_by   varchar(50)                             null,
    update_date datetime                                null,
    source      varchar(32) default 'ops-gateway-admin' null comment '网关来源'
)
    collate = utf8mb4_general_ci;

create table gateway_flow_rule
(
    id                      int auto_increment
        primary key,
    resource                varchar(30)              not null comment '资源名称，可以是网关中的 route 名称或者用户自定义的API 分组名称',
    resource_mode           int          default 0   null comment '规则是针对 API Gateway 的route（RESOURCE_MODE_ROUTE_ID）还是用户在 Sentinel 中定义的API 分组（RESOURCE_MODE_CUSTOM_API_NAME），默认是route',
    grade                   int          default 1   null comment '限流指标维度，同限流规则的grade 字段',
    count                   double                   not null comment '限流阈值',
    interval_sec            int unsigned default '1' null comment '统计时间窗口，单位是秒，默认是1 秒（目前仅对参数限流生效',
    control_behavior        int          default 0   null comment '流量整形的控制效果，同限流规则的 controlBehavior 字段，目前支持快速失败和匀速排队两种模式，默认是快速失败',
    burst                   int                      null comment '应对突发请求时额外允许的请求数目（目前仅对参数限流生效）',
    max_queueing_timeout_ms int          default 500 null comment '匀速排队模式下的最长排队时间，单位是毫秒，仅在匀速排队模式下生效',
    gateway_param_flow_item varchar(255)             null comment '参数限流配置。若不提供，则代表不针对参数进行限流，该网关规则将会被转换成普通流控规则；否则会转换成热点规则'
);

create table gateway_license
(
    id                 int unsigned auto_increment comment '主键id'
        primary key,
    authorization_code varchar(255) null comment '授权码'
)
    comment '许可证表' collate = utf8mb4_general_ci;

create table gateway_predicate
(
    id          int auto_increment
        primary key,
    type        varchar(30)                             not null,
    name        varchar(50)                             null,
    definition  varchar(255)                            not null,
    remarks     varchar(255)                            null,
    create_by   varchar(50)                             null,
    create_date datetime                                null,
    update_by   varchar(50)                             null,
    update_date datetime                                null,
    source      varchar(32) default 'ops-gateway-admin' null comment '网关来源'
)
    collate = utf8mb4_general_ci;

create table gateway_route
(
    id          int auto_increment
        primary key,
    route_name  varchar(50)                                  null,
    route_type  tinyint unsigned default '1'                 null comment '路由类型（1: 普通 2：黑名单）',
    predicates  varchar(100)                                 not null,
    filters     varchar(100)                                 null,
    uri         varchar(100)                                 not null,
    metadata    varchar(100)                                 null,
    route_order int              default 1                   null,
    remarks     varchar(255)                                 null,
    create_by   varchar(50)                                  null,
    create_date datetime                                     null,
    update_by   varchar(50)                                  null,
    update_date datetime                                     null,
    enabled     tinyint(1)       default 1                   null,
    source      varchar(32)      default 'ops-gateway-admin' null comment '网关来源'
)
    collate = utf8mb4_general_ci;

create index idx_route_type
    on gateway_route (route_type);

create table oauth2_authorization
(
    id                            varchar(100)  not null
        primary key,
    registered_client_id          varchar(100)  not null,
    principal_name                varchar(200)  not null,
    authorization_grant_type      varchar(100)  not null,
    authorized_scopes             varchar(1000) null,
    attributes                    blob          null,
    state                         varchar(500)  null,
    authorization_code_value      blob          null,
    authorization_code_issued_at  timestamp     null,
    authorization_code_expires_at timestamp     null,
    authorization_code_metadata   blob          null,
    access_token_value            blob          null,
    access_token_issued_at        timestamp     null,
    access_token_expires_at       timestamp     null,
    access_token_metadata         blob          null,
    access_token_type             varchar(100)  null,
    access_token_scopes           varchar(1000) null,
    oidc_id_token_value           blob          null,
    oidc_id_token_issued_at       timestamp     null,
    oidc_id_token_expires_at      timestamp     null,
    oidc_id_token_metadata        blob          null,
    refresh_token_value           blob          null,
    refresh_token_issued_at       timestamp     null,
    refresh_token_expires_at      timestamp     null,
    refresh_token_metadata        blob          null
)
    collate = utf8mb4_general_ci;

create table oauth2_authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
)
    collate = utf8mb4_general_ci;

create table oauth2_registered_client
(
    id                            varchar(100)                        not null
        primary key,
    client_id                     varchar(100)                        not null,
    client_id_issued_at           timestamp default CURRENT_TIMESTAMP not null,
    client_secret                 varchar(200)                        null,
    client_secret_expires_at      timestamp                           null,
    client_name                   varchar(200)                        not null,
    client_authentication_methods varchar(1000)                       not null,
    authorization_grant_types     varchar(1000)                       not null,
    redirect_uris                 varchar(1000)                       null,
    scopes                        varchar(1000)                       not null,
    client_settings               varchar(2000)                       not null,
    token_settings                varchar(2000)                       not null
)
    collate = utf8mb4_general_ci;

create table se_t
(
    id                      int unsigned auto_increment comment '主键id'
        primary key,
    encrypt_type            int unsigned default '101' not null comment '加密方式:  md5 101,RSA 102,sha256 103',
    client_id               varchar(50)  default ''    not null comment '客户端代码',
    client_key              varchar(888) default ''    not null comment '客户端密钥',
    client_key_bak          varchar(200) default ''    not null comment '备用密钥',
    status                  tinyint unsigned           not null comment '状态：1 可用，0 禁用',
    create_time             datetime                   not null comment '创建时间',
    update_time             datetime                   not null comment '更新时间',
    resource_ids            varchar(256)               null,
    scope                   varchar(256)               null,
    authorized_grant_types  varchar(256)               null,
    web_server_redirect_uri varchar(256)               null,
    authorities             varchar(256)               null,
    access_token_validity   int                        null,
    refresh_token_validity  int                        null,
    additional_information  varchar(4096)              null,
    autoapprove             varchar(256)               null,
    remarks                 varchar(200)               null comment 'client备注'
)
    comment '客户端密钥表' charset = utf8;

create table t_alarm_contact_t
(
    id                bigint unsigned auto_increment comment '主键'
        primary key,
    rule_id           varchar(50)                                not null comment '规则id',
    contact_target    tinyint                                    not null comment '联系目标（1:用户组 2:用户）',
    contact_target_id bigint                                     not null comment '联系目标id',
    contact_way       tinyint unsigned default '1'               not null comment '联系方式（1:邮箱）',
    create_time       datetime         default CURRENT_TIMESTAMP not null comment '创建时间',
    modify_time       datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '报警联系';

create index idx_rule
    on t_alarm_contact_t (rule_id);

create table t_alarm_notify_log_t
(
    id             bigint auto_increment comment '主键'
        primary key,
    alert_id       varchar(50)                                not null comment '告警id',
    rule_id        varchar(50)      default ''                not null comment '规则id',
    rule_name      varchar(50)                                not null comment '规则名称',
    notify_source  tinyint                                    not null comment '通知来源：1 keyword 2 skywalking',
    notify_message mediumtext                                 not null comment '报警通知内容',
    notify_time    datetime         default CURRENT_TIMESTAMP not null comment '报警通知时间',
    notify_status  tinyint unsigned default '0'               not null comment '报警通知状态：0 失败，1 成功',
    solve_status   tinyint unsigned default '0'               not null comment '报警跟踪：0 未解决，1 已解决',
    remark         varchar(500)     default ''                not null comment '备注',
    project        varchar(20)      default ''                not null comment '环境标识',
    create_time    datetime         default CURRENT_TIMESTAMP not null comment '创建时间',
    modify_time    datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '报警通知日志表';

create index idx_alert
    on t_alarm_notify_log_t (alert_id);

create index idx_create_time
    on t_alarm_notify_log_t (create_time);

create index idx_notify_status
    on t_alarm_notify_log_t (notify_status);

create index idx_notify_time
    on t_alarm_notify_log_t (notify_time);

create index idx_rule_name
    on t_alarm_notify_log_t (rule_name);

create index idx_solve_status
    on t_alarm_notify_log_t (solve_status);

create table t_dept
(
    DEPT_ID     bigint auto_increment comment '部门ID'
        primary key,
    PARENT_ID   bigint        not null comment '上级部门ID',
    DEPT_NAME   varchar(100)  not null comment '部门名称',
    ORDER_NUM   double(20, 0) null comment '排序',
    CREATE_TIME datetime      null comment '创建时间',
    MODIFY_TIME datetime      null comment '修改时间'
)
    charset = utf8;

create table t_devops_log_t
(
    id               bigint auto_increment comment '主键'
        primary key,
    application_name varchar(50)                         not null comment '应用名称',
    application_data text                                null comment '应用数据',
    result_list      varchar(1000)                       not null comment '执行标识',
    created_at       timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    updated_at       timestamp                           null comment '更新时间',
    created_by       bigint                              null comment '创建人',
    updated_by       bigint                              null comment '更新人'
)
    comment '持续集成日志表';

create table t_dict
(
    DICT_ID    bigint auto_increment comment '字典ID'
        primary key,
    KEYY       bigint       not null comment '键',
    VALUEE     varchar(100) not null comment '值',
    FIELD_NAME varchar(100) not null comment '字段名称',
    TABLE_NAME varchar(100) not null comment '表名'
)
    charset = utf8;

create table t_gen_config
(
    ID             bigint auto_increment comment '主键'
        primary key,
    SCHEMA_ID      varchar(50)                         null comment '数据源标识 ',
    PARENT_PACKAGE varchar(200)                        null,
    AUTHOR         varchar(20)                         null,
    OUTPUT_DIR     varchar(200)                        null comment '输出目录',
    MODULE_NAME    varchar(20)                         null comment '模块名称',
    ROUTER_NAME    varchar(50)                         null comment '路由名称',
    TEMPLATE_TYPE  varchar(8)                          null comment '模版类型 单表 /一对一 /一对多',
    MAIN_TABLE     json                                null comment '主表信息json',
    SUB_LIST       json                                null comment '子表列表信息 json',
    CREATED_AT     timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    UPDATED_AT     timestamp default CURRENT_TIMESTAMP null comment '更新时间',
    CREATED_BY     bigint    default -1                null comment '创建人',
    UPDATED_BY     bigint    default -1                null comment '更新人',
    IS_DELETED     int       default 0                 null comment '逻辑删除',
    VERSION        int       default 0                 null comment '版本号'
)
    comment '代码生成配置表' collate = utf8mb4_general_ci;

create table t_group
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(100)                           not null comment '组名称',
    remark      varchar(100) default ''                not null comment '备注',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    modify_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '用户组';

create index idx_create_time
    on t_group (create_time);

create index idx_name
    on t_group (name);

create table t_group_user_relation
(
    id          bigint auto_increment comment '主键'
        primary key,
    group_id    bigint                             not null comment '用户组id',
    user_id     bigint                             not null comment '用户id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    modify_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间'
)
    comment '用户组关联';

create index idx_group
    on t_group_user_relation (group_id);

create index idx_user
    on t_group_user_relation (user_id);

create table t_job
(
    JOB_ID          bigint auto_increment comment '任务id'
        primary key,
    BEAN_NAME       varchar(100) not null comment 'spring bean名称',
    METHOD_NAME     varchar(100) not null comment '方法名',
    PARAMS          varchar(200) null comment '参数',
    CRON_EXPRESSION varchar(100) not null comment 'cron表达式',
    STATUS          char(2)      not null comment '任务状态  0：正常  1：暂停',
    REMARK          varchar(200) null comment '备注',
    CREATE_TIME     datetime     null comment '创建时间'
)
    charset = utf8;

create table t_log
(
    ID          bigint auto_increment comment '日志ID'
        primary key,
    USERNAME    varchar(50) null comment '操作用户',
    OPERATION   text        null comment '操作内容',
    TIME        decimal(11) null comment '耗时',
    METHOD      text        null comment '操作方法',
    PARAMS      text        null comment '方法参数',
    IP          varchar(64) null comment '操作者IP',
    CREATE_TIME datetime    null comment '创建时间',
    location    varchar(50) null comment '操作地点'
)
    charset = utf8;

create table t_login_log
(
    USERNAME   varchar(100) not null comment '用户名',
    LOGIN_TIME datetime     not null comment '登录时间',
    LOCATION   varchar(255) null comment '登录地点',
    IP         varchar(100) null comment 'IP地址'
)
    charset = utf8;

create table t_menu
(
    MENU_ID     bigint auto_increment comment '菜单/按钮ID'
        primary key,
    PARENT_ID   bigint        not null comment '上级菜单ID',
    MENU_NAME   varchar(50)   not null comment '菜单/按钮名称',
    PATH        varchar(255)  null comment '对应路由path',
    COMPONENT   varchar(255)  null comment '对应路由组件component',
    PERMS       varchar(50)   null comment '权限标识',
    ICON        varchar(50)   null comment '图标',
    TYPE        char(2)       not null comment '类型 0菜单 1按钮',
    ORDER_NUM   double(20, 0) null comment '排序',
    CREATE_TIME datetime      not null comment '创建时间',
    MODIFY_TIME datetime      null comment '修改时间'
)
    charset = utf8;

create table t_namespace
(
    id                  bigint auto_increment comment '主键'
        primary key,
    custom_namespace_id varchar(256)           not null comment '命名空间id',
    namespace_name      varchar(50) default '' not null comment '命名空间名称',
    namespace_desc      varchar(255)           not null comment '描述',
    user_id             int                    null comment '用户id',
    create_time         timestamp              null comment '创建时间',
    update_time         timestamp              null comment '修改时间'
)
    collate = utf8mb4_bin;

create table t_project
(
    PROJECT_ID       bigint auto_increment comment '项目ID'
        primary key,
    NAME             varchar(100)           not null comment '项目名称',
    STATUS           char                   null comment '状态 0无效 1有效',
    DISCOVERY_URL    varchar(100)           not null comment '发现中心地址',
    REMARK           varchar(100)           null comment '项目描述',
    DISCOVERY_STATUS varchar(1)             null comment '发现中心状态 0下线 1在线',
    DISCOVERY_TYPE   varchar(20)            null comment '发现中心类型',
    CREATE_TIME      datetime               not null comment '创建时间',
    MODIFY_TIME      datetime               null comment '修改时间',
    namespace        varchar(20) default '' null comment 'ks8命名空间',
    constraint NAME
        unique (NAME)
)
    charset = utf8;

create table t_project_extra
(
    ID         bigint auto_increment comment '项目附加信息ID'
        primary key,
    PROJECT_ID bigint                                              not null comment '项目ID',
    TYPE       char(20) collate utf8mb4_general_ci      default '' not null comment '附加信息类型 apm:链路监控 grafana:grafana',
    URL        varchar(4000) collate utf8mb4_general_ci default '' not null comment '附加信息地址',
    EXTRA_INFO varchar(200) collate utf8mb4_general_ci             null comment '其他信息'
)
    collate = utf8mb4_bin;

create table t_role
(
    ROLE_ID     bigint auto_increment comment '角色ID'
        primary key,
    ROLE_NAME   varchar(10)  not null comment '角色名称',
    REMARK      varchar(100) null comment '角色描述',
    CREATE_TIME datetime     not null comment '创建时间',
    MODIFY_TIME datetime     null comment '修改时间'
)
    charset = utf8;

create table t_role_menu
(
    ROLE_ID bigint not null,
    MENU_ID bigint not null
)
    charset = utf8;

create table t_schema
(
    ID          bigint auto_increment comment '主键'
        primary key,
    SCHEMA_ID   varchar(50)                         null comment '数据源的唯一标识',
    SCHEMA_NAME varchar(50)                         not null comment '数据库名称',
    URL         varchar(200)                        not null comment '数据库连接地址',
    USERNAME    varchar(50)                         not null comment '用户名',
    PASSWORD    varchar(100)                        not null comment '密码',
    REMARK      varchar(200)                        null comment '描述',
    CREATED_AT  timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    CREATED_BY  bigint    default -1                null comment '创建人',
    UPDATED_AT  timestamp default CURRENT_TIMESTAMP null comment '更新时间',
    UPDATED_BY  bigint    default -1                null comment '更新人',
    IS_DELETED  tinyint   default 0                 not null comment '逻辑删除',
    VERSION     int       default 0                 not null comment '版本号'
)
    comment '数据源配置表' collate = utf8mb4_general_ci;

create table t_service
(
    id               bigint auto_increment comment '主键'
        primary key,
    env              varchar(20)  not null comment '环境标识',
    service_name     varchar(50)  not null comment '服务名称',
    description      varchar(200) null comment '服务简介',
    status           tinyint      null comment '服务状态',
    application_name varchar(20)  not null comment '应用名称',
    application_type int          not null comment '应用类型',
    updated_by       bigint       not null comment '更新人',
    created_at       datetime     not null comment '创建时间',
    updated_at       datetime     not null comment '更新时间',
    created_by       bigint       not null comment '创建人'
)
    comment '服务表' charset = utf8;

create table t_user
(
    USER_ID         bigint auto_increment comment '用户ID'
        primary key,
    USERNAME        varchar(50)        not null comment '用户名',
    PASSWORD        varchar(128)       not null comment '密码',
    SSO_ID          varchar(50)        not null comment 'SSO用户标识',
    DEPT_ID         bigint             null comment '部门ID',
    EMAIL           varchar(128)       null comment '邮箱',
    MOBILE          varchar(20)        null comment '联系电话',
    STATUS          char               not null comment '状态 0锁定 1有效',
    CREATE_TIME     datetime           not null comment '创建时间',
    MODIFY_TIME     datetime           null comment '修改时间',
    LAST_LOGIN_TIME datetime           null comment '最近访问时间',
    SSEX            char               null comment '性别 0男 1女 2保密',
    DESCRIPTION     varchar(100)       null comment '描述',
    AVATAR          varchar(100)       null comment '用户头像',
    USER_TYPE       tinyint default 0  not null comment '用户类型 0-普通用户 1-临时用户',
    USE_TIME        bigint  default -1 null comment '账号使用时长 单位:s'
)
    charset = utf8;

create table t_user_config
(
    USER_ID            bigint               not null comment '用户ID'
        primary key,
    THEME              varchar(10)          null comment '系统主题 dark暗色风格，light明亮风格',
    LAYOUT             varchar(10)          null comment '系统布局 side侧边栏，head顶部栏',
    MULTI_PAGE         char                 null comment '页面风格 1多标签页 0单页',
    FIX_SIDERBAR       char                 null comment '页面滚动是否固定侧边栏 1固定 0不固定',
    FIX_HEADER         char                 null comment '页面滚动是否固定顶栏 1固定 0不固定',
    COLOR              varchar(20)          null comment '主题颜色 RGB值',
    flip_switch        tinyint(1) default 0 not null comment '登陆背景图开关 0-关 1-开',
    DEFAULT_PROJECT_ID bigint               null comment '默认项目ID'
)
    charset = utf8;

create table t_user_project
(
    USER_ID    bigint not null comment '用户ID',
    PROJECT_ID bigint not null comment '项目ID'
)
    charset = utf8;

create table t_user_role
(
    USER_ID bigint not null comment '用户ID',
    ROLE_ID bigint not null comment '角色ID'
)
    charset = utf8;

create table tm_file_record
(
    ID                    int auto_increment comment '主键ID'
        primary key,
    FILE_TYPE             int                                null comment '存储类型',
    FILE_ADDRESS          varchar(500)                       not null comment '文件保存后地址',
    FILE_ADDRESS_COMPRESS varchar(500)                       null comment '文件保存后压缩地址',
    FILE_NAME             varchar(500)                       not null comment '文件保存前名称',
    IS_PRIVATE            int      default 1                 null comment '是否私有（1-共有；2-私有；默认1）',
    IS_DELETE             int      default 1                 null comment '是否删除（1-有效；0-无效；默认1）',
    CREATED_TIME          datetime default CURRENT_TIMESTAMP null comment '创建时间',
    UPDATED_TIME          datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    FILE_SIZE             bigint   default 0                 null
)
    collate = utf8mb4_general_ci;

create table tm_user
(
    id          int unsigned auto_increment comment '主键'
        primary key,
    user_name   varchar(20)                  not null comment '用户名',
    password    varchar(32)                  not null comment '密码',
    login_count tinyint unsigned default '1' not null comment '登录失败次数',
    create_time datetime                     not null comment '创建时间',
    update_time datetime                     not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint u_idx_uname
        unique (user_name)
)
    comment '用户表' charset = utf8;

