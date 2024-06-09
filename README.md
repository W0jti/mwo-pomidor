## Reports generator                                                                                 

This program can be used to generate reports based on Excel files prepared. Input files have to contain **`date`/`task`/`time(h)`** columns. 

Users can create three report types:

- **Report 1** - contains time for given projects and can be expanded to employee list in each project

   Example:  

   ```
      Project 1 - 100h
           Jan Kowalski - 60 h
           Krzysztof Kajak - 40 h
  
      Project 2 - 60h
           Ola Cola - 40 h
           Marek Zegarek - 20 h
   ```

- **Report 2** - contains total developers working time and their time devoted to specific projects

   Example:

   ```
      Jan Kowalski - 30h
           Project 1 - 20 h
           Project 2 - 10 h
  
      Paulina Ślimak - 50h
           Project 1 - 20 h
           Project 2 - 30 h
   ```

- **Report 3** - contains projects list sorted by longest time to shortest including tasks in specific projects, also sorted by time

   Example:

   ```
     Project 1 - 130h
           Task 1 - 100 h
           Task 2 - 30 h
  
     Project 2 - 90h
           Task 1 - 60 h
           Task 2 - 30 h
   ```

### Usage
By default report is generated in console, but there is option to generate output Excel file or PDF file with the bar charts.

To generate a report, type in terminal below command with relevant flags:

`java -jar target/mwo-pomidor-1.0-SNAPSHOT-jar-with-dependency.jar`

You can use the following flags:

**`[-c]` `[-d]` `[-e <export>]` `[-ep <path>]` `[-efn <filename>]``[-h]` `[-p <path>]` `[-r <reportOption>]` `[-f <dd/mm/yyyy>]` `[-t] <dd/mm/yyyy>``[-emp <"surname name">]`**


    
-
    -p,--path <place of source files here>

    Parameter to select source files. 
    This can be either catalog with several excel files or with just one.

    Example: -source resources/reporter-dane/reporter-dane/2012
    Example: -source C:\HoursWorked\TeamBlue`
---

    -r,--report-option <choose between 1, 2 or 3>

    Parameter to select report type.

    1 - generates total time for projects
    2 - generates total time for employees
    3 - generates total time for tasks

    Example: -source resources -r 1
---
    -d,--report-type

    Parameter without arguments which shows more detailed reports

    Example: -source resources -r 3 -d
---
    -f,--from <dd/mm/yyyy>

    This parameter filters report data from this date until now.
    When used with parameter -t, data is filtered for date range. 
    
    Example: -source resources -r 1 -d -f 01/01/2010
    Example: -source resources -r 1 -d -f 01/01/2010 -t 30/04/2010
---
    -t, --to <dd/mm/yyyy>

    This parameter filters report data until this date.
    When used with parameter -f, data is filtered for date range.

    Example: -source resources -r 1 -d -t 30/01/2010
    Example: -source resources -r 1 -d -t 30/01/2010 -f 01/01/2010
---
    -emp, --employee <"surname name">

    This parameter filters files for specified employee.

    Example: -source resources -r 2 -emp "Kowalski_Jan"
---
    -c,--chart

    Parameter without arguments which generates charts in the report. 

    Example: -source resources -r 2 -d -c
---
    -e,--export <filetype>

    This parameter defines if report should be generated as .pdf or .xlsx file

    Example: -source resources -r 3 -d -e pdf
    Example: -source resources -r 3 -d -e excel
---
    -ep,--export-path <pathname>

    Parameter which dictates in what location report should be generated.

    Example: -source resources -r 2 -d -c -ep
    Example: -source resources -r 2 -d -c -ep resources/generated_reports
---
    -efn,--export-file-name <filename>

    Parameter which dictates what report name should be.

    Example: -source resources -r 2 -d -c -efn
    Example: -source resources -r 2 -d -c -efn TestReport
---
    -h,--help

    Parameter without arguments which shows all commands with descriptions.

    Example: -h
---
    


Example:

`java -jar target/mwo-pomidor-1.0-SNAPSHOT-jar-with-dependencies.jar -p <path> -r 1 -e file_name`

generates report 1 (from folder/file located in path) to file_name file.



If u are lost use `-h` for help



---------------
**Made with ♡ by 🍅**
