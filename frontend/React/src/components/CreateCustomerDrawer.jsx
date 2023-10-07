import { 
    Button,
    Drawer,
    DrawerBody,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure,
    DrawerCloseButton,
    DrawerContent
} from "@chakra-ui/react"

import CreateCustomerForm from "./CreateCustomerForm";

const AddIcon=()=> "+";

const DrawerForm=({fetchCustomers})=>{
    const { isOpen, onOpen, onClose } = useDisclosure()
  return (
    <>
      <Button
        leftIcon={<AddIcon />}
        onClick={onOpen}
      >
        Create Customer
      </Button>

      <Drawer
        isOpen={isOpen}
        placement="right"
        size={"xl"}
        onClose={onClose}
      >
        <DrawerOverlay />
        <DrawerContent>
          <DrawerCloseButton />
          <DrawerHeader borderBottomWidth="1px">
            Create new customer
          </DrawerHeader>

          <DrawerBody>
            <CreateCustomerForm 
              fetchCustomers={fetchCustomers}
            />
          </DrawerBody>

          <DrawerFooter borderTopWidth="1px">
            <Button variant="outline" mr={3} onClick={onClose}>
              Cancel
            </Button>
            <Button
        leftIcon={"X"}
        onClick={onClose}
      >
        Close
      </Button>
      </DrawerFooter>
        </DrawerContent>
      </Drawer>
      </>
      );
}

export default DrawerForm;

