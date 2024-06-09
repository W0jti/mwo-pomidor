## Reports generator                                                                                 

This program can be used to generate reports based on Excel files prepared. Input files have to contain **`date`/`task`/`time(h)`** columns. 

Users can create three report types:

- **Report 1** - contains time for given projects

   Example:  

   `Project 1 - 30h`

   `Project 2 - 50h`

- **Report 2** - contains total developers working time and their time devoted to specific projects

   Example:

   `Jan Kowalski - 30h`
   ```
           Project 1 - 20 h
           Project 2 - 10 h
   ```

   `Paulina Ślimak - 50h`

   ```
           Project 1 - 20 h
           Project 2 - 30 h
   ```

- **Report 3** - contains projects list sorted by longest time to shortest including tasks in specific projects, also sorted by time

   Example:

   `Project 1 - 130h`
   ```
           Task 1 - 100 h
           Task 2 - 30 h
   ```

   `Project 2 - 90h`
   ```
           Task 1 - 60 h
           Task 2 - 30 h
   ```

### Usage
By default report is generated in console, but there is option to generate output Excel file with the bar charts.

To generate a report use:

`java -jar target/mwo-pomidor-1.0-SNAPSHOT-jar-with-dependency.jar`

You can use the following flags:

**`[-c]` `[-d]` `[-e <export>]` `[-h]` `[-p <path>]` `[-r <reportOption>]` `[-f]` `[-t]` `[-emp]`**

    -c,--chart                          Generate chart image
    -d,--report-type                    Report type: detailed
    -e,--export <export>                Export pdf
    -h,--help                           How to use
    -p,--path <path>                    Folder/file path
    -r,--report-option <reportOption>   Report type: 1/2/3
    -f,--from                           Start date
    -t, --to                            End date
    -emp, --employee                    Employee name


Example:

`java -jar target/mwo-pomidor-1.0-SNAPSHOT-jar-with-dependency.jar -p <path> -r 1 -e file_name`

generates report 1 (from folder/file located in path) to file_name file.



If u are lost use `-h` for help



---------------
**Made with ♡ by 🍅**
