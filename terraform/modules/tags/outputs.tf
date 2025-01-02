output "common_tags" {
    description = "Common tags for all resources"
    value       = local.common_tags
}

output "server_tags" {
    description = "Tags for server resources"
    value       = local.server_tags
}

output "gateway_tags" {
    description = "Tags for gateway resources"
    value       = local.gateway_tags
}

output "storage_tags" {
    description = "Tags for storage resources"
    value       = local.storage_tags
}

output "database_tags" {
    description = "Tags for database resources"
    value       = local.database_tags
}

output "service_worker_tags" {
    description = "Tags for service worker resources"
    value       = local.service_worker_tags
}
