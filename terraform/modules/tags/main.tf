locals {
    common_tags = {
        Service = var.project_name,
        Environment = var.environment
    }
    server_tags = merge(local.common_tags, {
        Role = "${var.project_name}-server"
    })
    gateway_tags = merge(local.common_tags, {
        Role = "${var.project_name}-gateway"
    })
    storage_tags = merge(local.common_tags, {
        Role = "${var.project_name}-storage"
    })
    database_tags = merge(local.common_tags, {
        Role = "${var.project_name}-db"
    })
    service_worker_tags = merge(local.common_tags, {
        Role = "service-worker"
    })
}
