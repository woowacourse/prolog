alter table graph_ability
drop
foreign key FK_GRAPH_ABILITY_ABILITY;

alter table graph_ability
    add constraint FK_GRAPH_ABILITY_ABILITY_WITH_ON_DELETE_CASCADE
        foreign key (ability_id)
            references ability (id) ON DELETE CASCADE;

