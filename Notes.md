- 5/12/24
    - ColorChoiceView: Changed Quit button to Cancel button

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