//
//  ContactsList.m
//  MagicApp
//

#import "ContactsList.h"

#import "AddressBook/AddressBook.h"

@interface ContactsList ()

@end

@implementation ContactsList

// The parameter is an array in which the first cell contains the filter string.
+(NSString *) getContacts:(NSArray *)params
{
    NSString *filter = [params objectAtIndex:0];
	NSString *result = @"";

	ABAddressBookRef addressBook = NULL;
	// ABAddressBookCreateWithOptions is iOS 6 and up.
	if (&ABAddressBookCreateWithOptions) {
		CFErrorRef error = nil;
		addressBook = ABAddressBookCreateWithOptions(NULL, &error);
		if (ABAddressBookGetAuthorizationStatus() == kABAuthorizationStatusNotDetermined) {
			ABAddressBookRequestAccessWithCompletion(addressBook, ^(bool granted, CFErrorRef error) {
				// First time access has been granted, add the contact				
			});
		}
		else if (ABAddressBookGetAuthorizationStatus() == kABAuthorizationStatusAuthorized) {
			// The user has previously given access, add the contact
		}
		else {
			// The user has previously denied access
			return @"Access to address book is denied. Please change the privacy setting in the setting app";
		}
	}

    // For iOS 5:
    //if (addressBook == NULL) {
    //    addressBook = ABAddressBookCreate();
    //}	

	CFArrayRef people = ABAddressBookCopyArrayOfAllPeople(addressBook);
	CFIndex n = ABAddressBookGetPersonCount(addressBook);
   
	for( int i = 0 ; i < n ; i++ )
	{
		ABRecordRef ref = CFArrayGetValueAtIndex(people, i);
		NSString *firstName = (__bridge NSString *)ABRecordCopyValue(ref, kABPersonFirstNameProperty);
		NSString *lastName = (__bridge NSString *)ABRecordCopyValue(ref, kABPersonLastNameProperty);
		NSLog(@"Name %@,%@", firstName,lastName);
	   
		// check the name filter
		if (
			(firstName != NULL && [firstName rangeOfString:filter options:NSCaseInsensitiveSearch].location != NSNotFound) 
			||
			(lastName != NULL && [lastName rangeOfString:filter options:NSCaseInsensitiveSearch].location != NSNotFound)
		   )
		{
	   
			ABMultiValueRef phones = ABRecordCopyValue(ref, kABPersonPhoneProperty);
			for(CFIndex j = 0; j < ABMultiValueGetCount(phones); j++)
			{
		   
				NSString *phoneNumber = (__bridge NSString *)ABMultiValueCopyValueAtIndex(phones, j);
				result = [result stringByAppendingString:[NSString stringWithFormat:@"%d,%@ %@,%@|", i, firstName,lastName,phoneNumber]];
				NSLog(@"%@", result);

			}
			CFRelease(phones);
		}
	}
	CFRelease(addressBook);
	CFRelease(people);
	return result;
}

@end
