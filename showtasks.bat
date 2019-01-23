call runcrud.bat
if "%ERRORLEVEL%" == "0" goto chrome
echo.
echo runcrud.bat has errors - breaking work
goto stoptomcat

:chrome
start chrome "http://localhost:8080/crud/v1/task/getTasks"

:stoptomcat
call %CATALINA_HOME%\bin\shutdown.bat
echo.
echo There were errors

:end
echo.
echo Work is finished.
