INSERT INTO public.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types,
                                         web_server_redirect_uri, authorities, access_token_validity,
                                         refresh_token_validity, additional_information, autoapprove)
VALUES ('resource', '', '$2a$10$MtkZFX0d.aGhP5iFEgOnmuRNtfEWcKk44m4jUkzniLivGrUWJXIni', 'read', 'authorization_code',
        'http://localhost:8020/login/oauth2/code', '', NULL, NULL, '{}', 'read');