output "bucket_name" {
    description = "The name of the S3 bucket"
    value       = aws_s3_bucket.bucket.id
}

output "bucket_arn" {
    description = "The ARN of the S3 bucket"
    value       = aws_s3_bucket.bucket.arn
}

output "public_access_block_id" {
    description = "The ID of the S3 bucket public access block configuration"
    value       = aws_s3_bucket_public_access_block.public_access.id
}
