@echo off
setlocal
set DIR=%~dp0
set CLASSPATH=%DIR%gradle\wrapper\gradle-wrapper.jar;%DIR%gradle\wrapper\gradle-wrapper-shared.jar
java -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
endlocal
