alter table conversations add update_date timestamptz;
update conversations conv set conv.update_date = (select msg.message_date from messages where conv.id = msg.conversation_id order by msg.message_date limit 1);
alter table conversations alter column update_date set not null;
