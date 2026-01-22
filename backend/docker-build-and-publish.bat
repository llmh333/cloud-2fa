
@echo off
setlocal enabledelayedexpansion

rem Usage: docker-build-and-publish.bat [imageName] [tag] [registry] [push]
rem Example: docker-build-and-publish.bat myrepo/cloud-2fa 1.2.3 myregistry.azurecr.io push

rem defaults
set IMAGE=cloud-2fa
set TAG=latest
set REGISTRY=
set PUSH=false

if not "%1"=="" set IMAGE=%~1
if not "%2"=="" set TAG=%~2
if not "%3"=="" set REGISTRY=%~3
if /I "%4"=="push" set PUSH=true

rem try to derive version from Maven if tag not provided or is 'latest'
if "%TAG%"=="latest" (
	if exist mvnw.cmd (
		for /f "usebackq delims=" %%v in (`mvnw.cmd help:evaluate -Dexpression=project.version -q -DforceStdout 2^>nul`) do (
			if not "%%v"=="" set TAG=%%v
		)
	) else if exist mvnw (
		for /f "usebackq delims=" %%v in (`mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2^>nul`) do (
			if not "%%v"=="" set TAG=%%v
		)
	)
)

rem full image name
if not "%REGISTRY%"=="" (
	set TARGET=%REGISTRY%/%IMAGE%:%TAG%
) else (
	set TARGET=%IMAGE%:%TAG%
)

echo Building project with Maven (skip tests)...
if exist mvnw.cmd (
	call mvnw.cmd -B -DskipTests package || goto :error
) else (
	mvn -B -DskipTests package || goto :error
)

echo Building Docker image: %TARGET%
docker build -f Dockerfile -t %TARGET% . || goto :error

if "%PUSH%"=="true" (
	echo Pushing %TARGET% ...
	docker push %TARGET% || goto :error
) else (
	if not "%REGISTRY%"=="" (
		echo Tagging and pushing to registry %REGISTRY% ...
		docker push %TARGET% || goto :error
	) else (
		echo Build complete, image available as %TARGET%
	)
)

echo Done.
endlocal
exit /b 0

:error
echo ERROR: command failed.
endlocal
exit /b 1

