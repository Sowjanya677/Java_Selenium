@echo off
REM This batch file compiles and runs your TestNG tests with Maven
REM Make sure you have Java 17+ installed (javac in PATH)

setlocal enabledelayedexpansion

REM Check if Maven is available
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven not found in PATH. Installing Maven...

    REM Download Maven
    echo Downloading Apache Maven 3.9.6...
    powershell -NoProfile -ExecutionPolicy Bypass -Command ^
        "[Net.ServicePointManager]::SecurityProtocol = [Net.ServicePointManager]::SecurityProtocol -bor [Net.SecurityProtocolType]::Tls12; ^
        $ProgressPreference = 'SilentlyContinue'; ^
        Invoke-WebRequest -Uri 'https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip' ^
        -OutFile '$env:TEMP\maven.zip'; ^
        Expand-Archive -Path '$env:TEMP\maven.zip' -DestinationPath '$env:TEMP' -Force; ^
        Move-Item -Path '$env:TEMP\apache-maven-3.9.6' -Destination 'C:\maven' -Force; ^
        Remove-Item '$env:TEMP\maven.zip' -Force; ^
        [Environment]::SetEnvironmentVariable('MAVEN_HOME', 'C:\maven', 'Machine'); ^
        $pathValue = [Environment]::GetEnvironmentVariable('Path', 'Machine'); ^
        if ($pathValue -notlike '*C:\maven*') { ^
            [Environment]::SetEnvironmentVariable('Path', $pathValue + ';C:\maven\bin', 'Machine'); ^
        }"

    if !errorlevel! equ 0 (
        echo Maven installed successfully.
        echo Please close and reopen this command prompt for changes to take effect.
        set "MAVEN_CMD=C:\maven\bin\mvn.cmd"
    ) else (
        echo Failed to install Maven. Please install it manually.
        exit /b 1
    )
) else (
    for /f %%i in ('where mvn') do set "MAVEN_CMD=%%i"
)

REM Navigate to project directory
cd /d "%~dp0"

REM Run Maven tests
echo.
echo ============================================
echo Running TestNG tests with Maven...
echo ============================================
echo.

"%MAVEN_CMD%" clean test -DskipTests=false

if %errorlevel% equ 0 (
    echo.
    echo ============================================
    echo Tests completed successfully!
    echo ============================================
) else (
    echo.
    echo ============================================
    echo Tests FAILED. Check the output above.
    echo ============================================
)

endlocal
pause

