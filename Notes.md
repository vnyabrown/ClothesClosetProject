- 5/13/24
    - Insert/Modify Color View - Displaying successful insert into db message should be notified from closet controller object.
    - Insert Color - Barcode prefix should only be 2 numbers
    - Modify/Delete Color View - Conntains delete button which shouldn’t be there, when modify button is clicked and nothing is selected, there should be an error message displayed (also fixed this in article type)
    - Insert Inventory View - Successful insert message is displayed when there is an error inserting into db

- 5/12/24
    - ColorChoiceView: Changed Quit button to Cancel button
    - InsertInventoryView: Set dropdowns for Colors, fields show descriptions
    - InsertInventoryView: Populated ArticleType field shows description

- 4/23/24
- Add constructors to ArticleType & Color
    - Create object from barcode (like from id)
    - Store data in properties objects?
- Auto-populate textfields with information from given objects
    - Check before processAction for all others, barcode field musn't be empty
    - Create a method in closet to either 1. Attempt to grab articleType from Barcode Prefix 2. Attempt to grab collection, just need to return whether it worked or not to verify our ArticleType 
    - Same as above with Color
- Fix ArticleType
    - Should have a Constructor to populate ArticleType from ID
    - Additionally, add constructor to populate ArticleType from BarcodePrefix (Same with Color)

- 4/25/24
    - requestFocus if/else not working, try to figure out how to fix this
        - Move requestFocus if/else-es, depending on checking barcode

- 4/30/24
    - working addInventory
    - set parseBarcode in creation of insertInventoryView
    - If invalid barcode, submit button will lock and request cancelling and entering a new barcode
    - Need to have fields reset so upon cancelling they can be repopulated again (bc the cancel will issues w/ this)