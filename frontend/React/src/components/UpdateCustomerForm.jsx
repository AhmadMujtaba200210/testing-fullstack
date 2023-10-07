import React from 'react';
import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import { Alert, AlertIcon, Box, FormLabel, Input, Select,Checkbox, Button, Stack } from '@chakra-ui/react';
import { updateCustomer } from '../services/client';
import notification from '../services/notification';

const MyTextInput = ({ label, ...props }) => {
  // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
  // which we can spread on <input>. We can use field meta to show an error
  // message if the field is invalid and it has been touched (i.e. visited)
  const [field, meta] = useField(props);
  return (
    <Box>
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Input className="text-input" {...field} {...props} />
      {meta.touched && meta.error ? (
        <Alert className="error" status={"error"} mt={2}>
          <AlertIcon />
          {meta.error}
        
          </Alert>
          
      ) : null}
    </Box>
  );
};

// And now we can use these
const UpdateCustomerForm = ({fetchCustomers,initialValues,customerEmail}) => {
  return (
    <>

      <Formik
        initialValues={initialValues}

        
        validationSchema={Yup.object({
          name: Yup.string()
            .max(15, 'Must be 15 characters or less')
            .required('Required'),
          email: Yup.string()
            .email('Invalid email address')
            .required('Required'),
          age: Yup.number()
            .min(16,"must be atleast 16 years")
            .max(65,"must be less than 65 years")
            .required('Required'),
        })}
        
        onSubmit={(updatedCustomer, { setSubmitting }) => {
          setSubmitting(true);
          updateCustomer(customerEmail,updatedCustomer)
            .then((response) => {
              if (response.status === 200) {
                // Handle success
                console.log("Customer updated");
                notification.successNotification(
                  "Customer updated",
                  `${updatedCustomer.name} has been updated`
                );
                fetchCustomers();
              }
            })
            .catch((error) => {
              console.error("Network error:", error); // Log network-related errors
              notification.errorNotification(
                error.code,
                error.response.data.message
              );
            })
            .finally(() => {
              setSubmitting(false);
            });
        }}        
      >
       {({isValid,isSubmitting,dirty})=>(
         <Form>
         <Stack spacing={"24px"}>

         <MyTextInput
           label="Name"
           name="name"
           type="text"
           placeholder="Jane"
         />

         

         <MyTextInput
           label="Email Address"
           name="email"
           type="email"
           placeholder="jane@formik.com"
         />

         <MyTextInput
           label="Age"
           name="age"
           type="number"
           placeholder="16"
         />

         <Button disabled={!(isValid&&dirty) || isSubmitting} type="submit"  mt={3}>Submit</Button>
         </Stack>
       </Form>
        
  )}
      </Formik>
    </>
  );
};

export default UpdateCustomerForm;