@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script, version 3.3.2
@REM ----------------------------------------------------------------------------
@echo off
setlocal

set MVNW_REPOURL=
set MAVEN_PROJECTBASEDIR=%~dp0
if "%MAVEN_PROJECTBASEDIR%"=="" set MAVEN_PROJECTBASEDIR=.

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_PROPERTIES="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties"

if not exist %WRAPPER_JAR% (
  echo Maven Wrapper jar not found. Downloading...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$p = '%MAVEN_PROJECTBASEDIR%\.mvn\wrapper'; if(!(Test-Path $p)){New-Item -ItemType Directory -Force -Path $p | Out-Null};" ^
    "$props = Get-Content '%WRAPPER_PROPERTIES%';" ^
    "$url = ($props | Where-Object { $_ -match '^wrapperUrl=' }) -replace '^wrapperUrl=','';" ^
    "Invoke-WebRequest -Uri $url -OutFile '%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar' -UseBasicParsing"
  if not exist %WRAPPER_JAR% (
    echo Failed to download Maven Wrapper jar.
    exit /b 1
  )
)

set MAVEN_OPTS=
set JAVA_EXE=java

where java >nul 2>nul
if not "%ERRORLEVEL%"=="0" (
  REM Try to use bundled JBR from IntelliJ / Android Studio if present
  if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1\jbr\bin\java.exe" (
    set "JAVA_EXE=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.1\jbr\bin\java.exe"
  ) else if exist "C:\Program Files\Android\Android Studio\jbr\bin\java.exe" (
    set "JAVA_EXE=C:\Program Files\Android\Android Studio\jbr\bin\java.exe"
  ) else (
    echo Java not found. Please install JDK 17 and ensure java is available.
    exit /b 1
  )
)

"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -classpath %WRAPPER_JAR% org.apache.maven.wrapper.MavenWrapperMain %*
exit /b %ERRORLEVEL%
