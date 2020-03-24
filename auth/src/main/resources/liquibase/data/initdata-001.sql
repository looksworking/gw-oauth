INSERT INTO public.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types,
                                         web_server_redirect_uri, authorities, access_token_validity,
                                         refresh_token_validity, additional_information, autoapprove)
VALUES ('gateway', '', '$2a$10$MtkZFX0d.aGhP5iFEgOnmuRNtfEWcKk44m4jUkzniLivGrUWJXIni', 'read', 'openid,refresh_token,authorization_code',
        'http://172.10.16.1:8030/login/oauth2/code', '', NULL, NULL, '{}', 'read');