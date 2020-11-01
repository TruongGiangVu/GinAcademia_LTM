# Server

Run by `Main.java` outside (in default package, if you use Eclipse)

## FolderTree

- Server GUI
- Socket: connect to server.
  - Request: class for sending request
  - Response: class for receiving response
  - Class Server: Manage all players
  - Class ClientHandler: Handle each player
- DAO: classes for connecting MongoDB
- BUS: classes for processing business
- Module: extend feature and design
- Model: object classes

## OutsideFolder

- libraries: libs use in app (.jar)
- seedData: seed data (file json) in MongoDB
