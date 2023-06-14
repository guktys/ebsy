package com.example.EbayMaven;

import java.io.IOException;

import com.ebay.sdk.ApiContext;
import com.ebay.sdk.ApiCredential;
import com.ebay.sdk.call.AddItemCall;
import com.ebay.sdk.helper.ConsoleUtil;
import com.ebay.sdk.util.eBayUtil;
import com.ebay.soap.eBLBaseComponents.AmountType;
import com.ebay.soap.eBLBaseComponents.BuyerPaymentMethodCodeType;
import com.ebay.soap.eBLBaseComponents.CategoryType;
import com.ebay.soap.eBLBaseComponents.CountryCodeType;
import com.ebay.soap.eBLBaseComponents.CurrencyCodeType;
import com.ebay.soap.eBLBaseComponents.FeesType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingDurationCodeType;
import com.ebay.soap.eBLBaseComponents.ListingTypeCodeType;
import com.ebay.soap.eBLBaseComponents.ReturnPolicyType;
import com.ebay.soap.eBLBaseComponents.ShippingDetailsType;
import com.ebay.soap.eBLBaseComponents.ShippingServiceCodeType;
import com.ebay.soap.eBLBaseComponents.ShippingServiceOptionsType;
import com.ebay.soap.eBLBaseComponents.ShippingTypeCodeType;

public class ApplicationAddItem {

    public static void main(String[] args) {

        try {

            System.out.print("\n");
            System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
            System.out.print("+ Welcome to eBay SDK for Java Sample +\n");
            System.out.print("+  - ConsoleAddItem                   +\n");
            System.out.print("+++++++++++++++++++++++++++++++++++++++\n");
            System.out.print("\n");

            // [Step 1] Initialize eBay ApiContext object
            System.out.println("===== [1] Account Information ====");
            ApiContext apiContext = getApiContext();

            // [Step 2] Create a new item object.
            System.out.println("===== [2] Item Information ====");
            ItemType item = buildItem();

            // [Step 3] Create call object and execute the call.
            System.out.println("===== [3] Execute the API call ====");
            System.out.println("Begin to call eBay API, please wait ...");
            AddItemCall api = new AddItemCall(apiContext);
            api.setItem(item);
            FeesType fees = api.addItem();
            System.out.println("End to call eBay API, show call result ...");
            System.out.println();

            // [Setp 4] Display results.
            System.out.println("The list was listed successfully!");

            double listingFee = eBayUtil.findFeeByName(fees.getFee(), "ListingFee").getFee().getValue();
            System.out.println("Listing fee is: " + new Double(listingFee).toString());
            System.out.println("Listed Item ID: " + item.getItemID());
        }
        catch(Exception e) {
            System.out.println("Fail to list the item.");
            e.printStackTrace();
        }
    }


    /**
     * Populate eBay SDK ApiContext object with data input from user
     * @return ApiContext object
     */
    private static ApiContext getApiContext() throws IOException {
        String input;
        ApiContext apiContext = new ApiContext();

        // set Api Token to access eBay Api Server
        ApiCredential cred = apiContext.getApiCredential();
        input = ConsoleUtil.readString("Enter your eBay Authentication Token: ");
        cred.seteBayToken(input);

        // set Api Server Url
        input = ConsoleUtil.readString("Enter eBay SOAP server URL (e.g., https://api.ebay.com/wsapi): ");
        apiContext.setApiServerUrl(input);

        return apiContext;
    }

    /**
     * Build a sample item
     * @return ItemType object
     */
    private static ItemType buildItem() throws IOException {

        String input;
        ItemType item = new ItemType();

        // item title
        item.setTitle(ConsoleUtil.readString("Title: "));
        // item description
        item.setDescription(ConsoleUtil.readString("Description: "));

        // listing type
        item.setListingType(ListingTypeCodeType.CHINESE);
        // listing price
        item.setCurrency(CurrencyCodeType.USD);
        input = ConsoleUtil.readString("Start Price: ");
        AmountType amount = new AmountType();
        amount.setValue(Double.valueOf(input));
        item.setStartPrice(amount);

        // listing duration
        item.setListingDuration(ListingDurationCodeType.DAYS_3.value());

        // item location and country
        item.setLocation(ConsoleUtil.readString("Location: "));
        item.setCountry(CountryCodeType.US);

        // listing category
        CategoryType cat = new CategoryType();
        cat.setCategoryID(ConsoleUtil.readString("Primary Category (e.g., 30022): "));
        item.setPrimaryCategory(cat);

        // item quality
        item.setQuantity(new Integer(1));

        // payment methods
        item.setPaymentMethods(new BuyerPaymentMethodCodeType[]
                {BuyerPaymentMethodCodeType.PAY_PAL});
        // email is required if paypal is used as payment method
        item.setPayPalEmailAddress("me@ebay.com");

        // item condition, New
        item.setConditionID(1000);

        // handling time is required
        item.setDispatchTimeMax(Integer.valueOf(1));

        // shipping details
        item.setShippingDetails(buildShippingDetails());

        // return policy
        ReturnPolicyType returnPolicy = new ReturnPolicyType();
        returnPolicy.setReturnsAcceptedOption("ReturnsAccepted");
        item.setReturnPolicy(returnPolicy);

        return item;
    }
    /**
     * Build sample shipping details
     * @return ShippingDetailsType object
     */
    private static ShippingDetailsType buildShippingDetails()
    {
        // Shipping details.
        ShippingDetailsType sd = new ShippingDetailsType();

        sd.setApplyShippingDiscount(new Boolean(true));
        AmountType amount =new AmountType();
        amount.setValue(2.8);
        sd.setPaymentInstructions("eBay Java SDK test instruction.");

        // Shipping type and shipping service options
        sd.setShippingType(ShippingTypeCodeType.FLAT);
        ShippingServiceOptionsType shippingOptions = new ShippingServiceOptionsType();
        shippingOptions.setShippingService(
                ShippingServiceCodeType.SHIPPING_METHOD_STANDARD.value());
        amount = new AmountType();
        amount.setValue(2.0);
        shippingOptions.setShippingServiceAdditionalCost(amount);
        amount = new AmountType();
        amount.setValue(10);
        shippingOptions.setShippingServiceCost(amount);
        shippingOptions.setShippingServicePriority(new Integer(1));
        amount = new AmountType();
        amount.setValue(1.0);
        shippingOptions.setShippingInsuranceCost(amount);

        sd.setShippingServiceOptions(new ShippingServiceOptionsType[]{shippingOptions});

        return sd;
    }


}
