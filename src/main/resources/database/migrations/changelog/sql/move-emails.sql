UPDATE consumer_config config, consumer c
SET config.email_for_notifications = c.email
WHERE config.id = c.id;
