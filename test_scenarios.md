## Functional tests scenarios 

for manual testing performed for specific data package: `src/resources/reporter-dane/reporter-dane/2012`

- **Report 1**:
  
basic version (project list with time)

1) Check if the report has been generated
2) Check if all projects are listed
3) Check if proper time (h) is given for each project
4) Check proper chart generation
5) Check proper date scope for report results
6) Check if report for specific employee can be generated + proper data for that employee

extended version (+ employees with time for each project)

7) Check if the report has been generated 
8) Check if all projects for each employee are listed
9) Check if time (h) for employee =  project time sum
10) Check proper chart generation
11) Check if all employees are included
12) Check proper chart generation 
13) Check proper date scope for report results 
14) Check if report for specific employee with projects can be generated + proper data for that employee

- **Report 2**:

basic version (employee list with time)

1) Check if the report has been generated
2) Check if all employees are listed
3) Check if proper time (h) is given for each employee
4) Check proper chart generation
5) Check proper date scope for report results
6) Check if report for specific employee can be generated + proper data for that employee

extended version (+ projects with time for each employee)

7) Check if the report has been generated 
8) Check if all projects for each employee are listed
9) Check if time (h) for employee =  project time sum
10) Check proper chart generation
11) Check if all employees are included
12) Check proper chart generation 
13) Check proper date scope for report results 
14) Check if report for specific employee with projects can be generated + proper data for that employee

- **Report 3**:

basic version (project list with time sorted by duration)

1) Check if the report has been generated
2) Check if all projects are listed
3) Check if proper time (h) is given for each project
4) Check sorting by project duration
5) Check proper chart generation
6) Check proper date scope for report results
7) Check if report for specific employee can be generated + proper data for that employee

extended version (+ tasks with time for each project, both sorted by duration)

7) Check if the report has been generated
8) Check sorting by project duration
9) Check sorting by task duration for each project
10) Check if all tasks for each project are listed
11) Check if time (h) for project =  tasks time sum
12) Check proper chart generation
13) Check if all tasks are included
13) Check proper chart generation
14) Check proper date scope for report results
15) Check if report for specific employee with projects can be generated + proper data for that employee
