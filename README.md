# InforeApp

This Spring Boot Infore application provides an API to list GitHub repositories.

## Endpoints

### List GitHub Repositories
- **URL**: `/github/repositories/{username}`
- **Method**: `GET`
- **Request Header**: `Accept: application/json`
- **Response Body**:
  ```json
  {
      "repositoryName": "Repository Name",
      "ownerLogin": "Owner Login",
      "branches": [
          {
              "name": "Branch Name",
              "lastCommitSha": "Last Commit SHA"
          }
      ]
  }
