#!/bin/bash

# 현재 작업 디렉토리 이름 가져오기 (dev, prod 등)
ENVIRONMENT=$(basename "$PWD")

# 워크스페이스 선택 또는 생성
terraform workspace select "$ENVIRONMENT" || terraform workspace new "$ENVIRONMENT"

# 변경 사항 계획
terraform plan -out=tfplan

# 변경 사항 적용
terraform apply tfplan
