/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/10 0:33:53                            */
/*==============================================================*/


drop table if exists consumable_record;

drop table if exists consumables_admins;

drop table if exists consumables_items;

drop table if exists consumables_user;

/*==============================================================*/
/* Table: consumable_record                                     */
/*==============================================================*/
create table consumable_record
(
   cr_id                int unsigned not null auto_increment,
   ci_id                int unsigned not null,
   cu_id                int unsigned not null,
   ca_id                int unsigned not null,
   cr_application_datetime datetime not null,
   cr_confirmed_datetime datetime,
   primary key (cr_id)
);

/*==============================================================*/
/* Table: consumables_admins                                    */
/*==============================================================*/
create table consumables_admins
(
   ca_id                int unsigned not null auto_increment,
   ca_name              char(15) not null,
   ca_password          char(20) not null,
   ca_contact           char(20) not null,
   ca_login_datetime    datetime not null,
   primary key (ca_id)
);

/*==============================================================*/
/* Table: consumables_items                                     */
/*==============================================================*/
create table consumables_items
(
   ci_id                int unsigned not null auto_increment,
   ci_name              char(20) not null,
   ci_stocks            int unsigned not null,
   ci_added_datetime    datetime not null,
   ci_modified          datetime not null,
   primary key (ci_id)
);

/*==============================================================*/
/* Table: consumables_user                                      */
/*==============================================================*/
create table consumables_user
(
   cu_id                int unsigned not null auto_increment,
   cu_name              char(15) not null,
   cu_contact           char(20) not null,
   cu_password          char(20) not null,
   cu_regiset_time      datetime not null,
   cu_latest_login      datetime not null,
   primary key (cu_id)
);

alter table consumable_record add constraint FK_application foreign key (cu_id)
      references consumables_user (cu_id) on delete restrict on update restrict;

alter table consumable_record add constraint FK_confirm foreign key (ca_id)
      references consumables_admins (ca_id) on delete restrict on update restrict;

alter table consumable_record add constraint FK_consume foreign key (ci_id)
      references consumables_items (ci_id) on delete restrict on update restrict;

