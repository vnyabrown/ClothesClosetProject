# Before changing any information in this file first
# enter the 2 commands below into the terminal within the git directory

# Command: git update-index --skip-worktree dbConfig.ini

# Command: git update-index --skip-worktree ConfigureDB.py

# This will stop you from pushing local changes to this file
# and over writing the template.

import os
local_username = "Enter YOUR USERNAME HERE"
local_password = "ENTER YOUR PASSWORD HERE"
local_db_name = "ENTER YOUR DATABASE NAME HERE"
server = "localhost"

choice = int(input("Enter 1 to connect to your local database\nEnter 2 to connect to CSDB\n"))

file_path = "dbConfig.ini"

if choice == 1 or choice == 2:
    with open(file_path, "w") as file:
        if choice == 1:
            file.write("username=" + local_username)
            file.write("\npassword=" + local_password)
            file.write("\ndbName=" + local_db_name)
            file.write("\nserver=" + server)
            print("dbConfig.ini updated for localhost")
        else:
            file.write("username=" + "ahadd1")
            file.write("\npassword=" + "123")
            file.write("\ndbName=" + "spr24_csc429_ahadd1")
            file.write("\nserver=" + "csdb.brockport.edu")
            print("dbConfig.ini updated for CSDB")
        file.close
else:
    print("Not a choice")




