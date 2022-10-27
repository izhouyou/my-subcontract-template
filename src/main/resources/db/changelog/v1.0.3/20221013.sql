--liquibase formatted sql
--changeset izhouy:2022101304
CREATE TABLE ${database.liquibaseSchemaName}."log_info" (
    "id" bigserial constraint log_info_pk primary key,
    "module" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "type" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "message" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "req_param" text COLLATE "pg_catalog"."default",
    "res_param" text COLLATE "pg_catalog"."default",
    "take_up_time" int8,
    "user_id" varchar(64) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "user_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "method" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "uri" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "ip" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "version" varchar(64) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(6) DEFAULT NULL::timestamp without time zone
);
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."id" IS '主键id';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."module" IS '功能模块';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."type" IS '操作类型';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."message" IS '操作描述';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."req_param" IS '请求参数';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."res_param" IS '响应参数';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."take_up_time" IS '耗时';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."user_id" IS '操作用户id';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."user_name" IS '操作用户名称';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."method" IS '操作方面';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."uri" IS '请求url';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."ip" IS '请求IP';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."version" IS '版本号';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_info"."create_time" IS '创建时间';
COMMENT ON TABLE ${database.liquibaseSchemaName}."log_info" IS '操作日志';

CREATE TABLE ${database.liquibaseSchemaName}."log_error_info" (
    "id" bigserial constraint log_error_info_pk primary key,
    "req_param" text COLLATE "pg_catalog"."default",
    "name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "message" text COLLATE "pg_catalog"."default",
    "user_id" varchar(64) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "user_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "method" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "uri" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "ip" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "version" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
    "create_time" timestamp(6) DEFAULT NULL::timestamp without time zone
)
;
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."id" IS '主键id';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."req_param" IS '请求参数';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."name" IS '异常名称';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."message" IS '异常信息';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."user_id" IS '操作用户id';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."user_name" IS '操作用户名称';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."method" IS '请求方法';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."uri" IS '请求url';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."ip" IS '请求IP';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."version" IS '版本号';
COMMENT ON COLUMN ${database.liquibaseSchemaName}."log_error_info"."create_time" IS '创建时间';
COMMENT ON TABLE ${database.liquibaseSchemaName}."log_error_info" IS '操作日志异常信息';
