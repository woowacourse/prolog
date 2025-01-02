output "ec2_role_name" {
    description = "The name of the IAM Role created for EC2 instances"
    value       = aws_iam_role.ec2_role.name
}

output "ec2_role_arn" {
    description = "The ARN of the IAM Role created for EC2 instances"
    value       = aws_iam_role.ec2_role.arn
}

output "s3_access_policy_name" {
    description = "The name of the IAM Policy created for S3 access"
    value       = aws_iam_policy.s3_access_policy.name
}

output "s3_access_policy_arn" {
    description = "The ARN of the IAM Policy created for S3 access"
    value       = aws_iam_policy.s3_access_policy.arn
}

output "s3_access_policy_attachment_id" {
    description = "The ID of the IAM Role Policy Attachment for the S3 access policy"
    value       = aws_iam_role_policy_attachment.s3_access_attachment.id
}

output "code_deploy_role_name" {
    description = "The name of the IAM Role created for CodeDeploy"
    value       = aws_iam_role.code_deploy_role.name
}

output "code_deploy_role_arn" {
    description = "The ARN of the IAM Role created for CodeDeploy"
    value       = aws_iam_role.code_deploy_role.arn
}

output "code_deploy_policy_name" {
    description = "The name of the IAM Policy created for CodeDeploy"
    value       = aws_iam_policy.code_deploy_policy.name
}

output "code_deploy_policy_arn" {
    description = "The ARN of the IAM Policy created for CodeDeploy"
    value       = aws_iam_policy.code_deploy_policy.arn
}

output "code_deploy_policy_attachment_id" {
    description = "The ID of the IAM Role Policy Attachment for the CodeDeploy"
    value       = aws_iam_role_policy_attachment.code_deploy_attachment.id
}
