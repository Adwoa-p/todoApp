CREATE SEQUENCE IF NOT EXISTS task_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE task
(
    task_id        INTEGER NOT NULL,
    title          VARCHAR(255),
    description    VARCHAR(255),
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    deadline       TIMESTAMP WITHOUT TIME ZONE,
    date_completed TIMESTAMP WITHOUT TIME ZONE,
    priority       VARCHAR(255),
    status         VARCHAR(255),
    is_deleted     BOOLEAN NOT NULL,
    CONSTRAINT pk_task PRIMARY KEY (task_id)
);