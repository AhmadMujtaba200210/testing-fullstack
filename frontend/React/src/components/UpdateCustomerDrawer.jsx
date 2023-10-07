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
import UpdateCustomerForm from "./UpdateCustomerForm";

const UpdateCustomerDrawer=({fetchCustomers,initialValues,customerEmail})=>{
    const { isOpen, onOpen, onClose } = useDisclosure()
  return (
    <>
      <Button
        
        onClick={onOpen}
      >
        Update Customer
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
            Update {initialValues.name}'s profile
          </DrawerHeader>

          <DrawerBody>
            <UpdateCustomerForm
              fetchCustomers={fetchCustomers}
              initialValues={initialValues}
              customerEmail={customerEmail}
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

export default UpdateCustomerDrawer;

