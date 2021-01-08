package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.truck.transfly.R;

public class TermsAndCondition extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_terms_and_condition);

        TextView desc =findViewById(R.id.desc);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        findViewById(R.id.agree_terms_condition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        desc.setText("Thank you for using TransFly (“App”) Services. These Terms of Use, together with TransFly Privacy Policy, shall apply to the users of the app as and when they visit, browse, access data and, place orders, upload or download documents, access or otherwise use the App.\n" +
                "By using the App, you acknowledge and signify that you have read, understood, and agree to be bound by these Terms and Conditions. You must agree to these Terms and Conditions in order to access or use the App. You may not use the services if you do not accept the terms or are unable to be bound by the terms.\n" +
                "Scope of these terms\n" +
                "These Terms of Use only apply to your activities when using the App or its contents. The following General Terms and Conditions apply to all services rendered between TransFly and Users of this app/site whether as a consignor, consignee, lorry/ truck (Vehicle) owner, Transporter, Agent or in any other capacity. The TransFly through this app provides a platform that enables consignors, consignees and vehicle owners, transport contractors and agents to meet, approach, compare, place orders or enter into contracts or transactions to make easy and seamless transportation, conveyance or movement goods from one place to another. Deviations from these conditions are only binding if confirmed in writing and these General Terms and Conditions will apply to all additional services or revised services.\n" +
                "User’s Obligation\n" +
                "The users agree not to:\n" +
                "    • Use the services to obtain unauthorized access to the services, systems, network or data;\n" +
                "    • Violate any applicable laws or regulations;\n" +
                "    • Make available any content that you do not have the right to make available or that infringes any patent, trademark, copyright or other proprietary rights of any person or entity;\n" +
                "    • Make available viruses or any other computer code, files, programs or content designed to interrupt destroy or limit the functionality of the services and affect other users;\n" +
                "    • Interfere with or disrupt the services or servers, systems or networks connected to the services in any way.\n" +
                "Confidentiality\n" +
                "The TransFly app shall in no event be liable for any direct, indirect, special, consequential or other damages arising out of the use of this app or any information or images contained therein, including without limitation, lost profits, business interruption and loss of programs or other data. Each party is obliged to treat any data and information not publicly accessible as confidential and to use these exclusively for the purpose intended.\n" +
                "The confidentiality rule does not apply to data and information that must be passed on to third parties, especially public authorities, due to legal obligations. The other party is to be informed about such obligation without delay.\n" +
                "Limitation of Liability and Warranties\n" +
                "The TransFly app:\n" +
                "    • Shall not take responsibility or liability for the pricing and payment terms of contract entered between Vehicle owner and consignor/consignee, transport contractors and agents using this app as a platform;\n" +
                "    • Shall not take responsibility or liability for any dispute arising between consignor or consignee or vehicle owner(or Lorry provider) or driver regarding quantity, quality, packing, assembling, etc. of goods conveyed with the help of this Application;\n" +
                "    • Shall be harmless from any and all claims, losses, damages, and liabilities, costs and expenses, including without limitation legal fees and expenses, arising out of or related to use or misuse of the services or of the app, any violation by user of these Terms and Conditions, or any breach of the representations, warranties, and covenants made herein;\n" +
                "    • Shall not be liable for any late pickup, late delivery, early arrival, early delivery any waiting charges and also if user procure the freight without pickup service and/or delivery service, they themselves are responsible for delivering the shipment to the designated drop-off and/or pickup point;\n" +
                "    • Shall not be responsible for goods or services or the terms on which services may be procured or availed using this application as a platform. If user chooses to procure or avail such goods or services, user should carefully evaluate them and the terms upon which you are availing them;\n" +
                "    • Shall not be responsible for any natural loss, damage, deterioration of goods involved including on account of pilferage, theft, accident, natural calamity or any other similar cause;\n" +
                "    • Shall not be liable for any loss to vehicles using the TransFly app services or conduct of the drivers of the vehicles. Users may notify complaints against driver of any vehicle that you may have hired using this platform;\n" +
                "    • Shall not be liable for performance of service as earlier agreed by user parties; and\n" +
                "    • May ask you for feedback of listed vehicles users from whom the users would have availed services.\n" +
                "The user of app acknowledges and agrees that they shall be liable for checking the contents, packing, sealing, addressing, labeling and handling of the goods, carriage, shipping instructions or waybill.\n" +
                "Intellectual Property\n" +
                "The TransFly name and logo, and other trademarks, service marks, graphics and logos used in connection with the App are trademarks of Transfly Infotech (India) Private Limited. The TransFly Trademarks may not be copied, imitated or used, in whole or in part, without the prior written permission of the applicable trademark holder. The App and the content featured in the App are protected by copyright, trademark, and other intellectual property and proprietary rights which are\n" +
                "General law and Jurisdiction\n" +
                "Except as otherwise set forth in these Terms, these Terms shall be governed by Indian laws and the parties submit to the exclusive jurisdiction of Keonjhar district courts (Keonjhar, Odisha) to resolve any dispute between them arising under or in connection with these App Terms.\n" +
                "If any provision (or part of a provision) of these App Terms is found by a court or administrative body of competent jurisdiction to be invalid, unenforceable or illegal, such term, condition or provision will to that extent be severed from the remaining terms, conditions and provisions which will continue to be valid to the fullest extent permitted by law, reserved to trademark holder and its licensors.\n" +
                "Any dispute, conflict, claim or controversy arising out of or broadly in connection with or relating to the Services or these Terms, including those relating to its validity, its construction or its enforceability (any “Dispute”) shall be first mandatorily submitted to mediation proceedings in terms of the Indian law. If such Dispute has not been settled within sixty (60) days after a request for mediation has been submitted under the Indian laws, such Dispute can be referred to and shall be exclusively and finally resolved by arbitration under the Arbitration and Conciliation Act, 1996 (“Act”). The Dispute shall be resolved by one (1) arbitrator to be appointed by TransFly Infotech (India) Private Limited. The place of both mediation and arbitration shall be Keonjhar, Odisha, India. The language of the mediation and/or arbitration shall be English, unless you do not speak English, in which case the mediation and/or arbitration shall be conducted in both English and your native language. The existence and content of the mediation and arbitration proceedings, including documents and briefs submitted by the parties, any correspondence from the mediator, and correspondence, orders and awards issued by the sole arbitrator, shall remain strictly confidential and shall not be disclosed to any third party without the express written consent from the other party unless: (i) the disclosure to the third party is reasonably required in the context of conducting the mediation or arbitration proceedings; and (ii) the third party agrees unconditionally in writing to be bound by the confidentiality obligation stipulated herein.\n" +
                "\n" +
                "Other Terms and Conditions: Please read these carefully, as using our TransFly App services will mean that you have accepted these terms and conditions. \n" +
                "Terms and Conditions of Service: Vehicle Owners and Their Authorised Representative\n" +
                "    • Vehicle owner is someone who is mentioned on the Government issued vehicle registration certificate (RC) \n" +
                "    • Authorised Representative is the one who has been authorised by the vehicle owner through Power Of Attorney or any such government specified authorization document and acts in the capacity of Vehicle Owner.                                                       \n" +
                "    • Payments will be made in 4-5 working days after the Challan has been submitted (dependent on factors like Bank/Govt/ other festival season holidays etc).                                               \n" +
                "    • Our On-Road services on Vehicle Breakdown, Accidents and other assistance is only for our registered members and we will only be providing assistance, any expenses incurred would be borne by vehicle owner or the Authorised Representative himself.   \n" +
                "    • Any theft issues, material losses during transportation, vehicle maintenance costs, repair costs, driver behavior related issues will not fall under the TransFly Infotech Pvt Ltd scope and would only be addressed by vehicle owner or the Authorised Representative.   \n" +
                "    • TransFly Infotech Pvt Ltd would not be responsible for no-loading on the following occasions – natural calamities, strikes, festival times/holidays and lack of permissions from mining authorities or any other uncontrollable factors.\n" +
                "    • TransFly Infotech Pvt Ltd works as per the Market Rates however its dependent on market volatility factors, TransFly Infotech Pvt Ltd will stick to the market rates offered for the said loading date.\n" +
                "    • TDS/GST or any other taxes as applicable would be deducted as per Government rules.\n" +
                "    • Shortage of material during transportation would be deducted from the payment.\n" +
                "    • No payment would be cleared on Thu and Govt/Bank Holidays.\n" +
                "    • Document submissions such as RC, Pan Card, Insurance etc would be needed to be submitted as per Government Regulation and any such change from the Govt would need to be adhered to by all the parties concerned.\n" +
                "    • 24x7 on-road/customer care support might get impacted during the festival seasons or any other uncontrollable factors like Corona, natural calamities etc \n" +
                "    • Subscription charges would be applicable on monthly basis per vehicle and service charges would be applicable on each Challan. TransFly Infotech Pvt Ltd has all the rights to revoke/revise as deem fit by the company strategies and would be mentioned on the Challan from the company whenever such charges are applicable and deducted for a vehicle owner. For more information, Vehicle owners are advised to go through the company website or speak to customer care, whatsapp us or email for any clarifications on this, details of all these contact numbers and emails are available on company website/s.\n" +
                "    • TransFly Infotech Company holds the right to change any of the terms and conditions of service and use and any other policies without a prior notification.\n" +
                "    • All disputes would be subject to the exclusive jurisdiction of honorable Keonjhar District Courts only.\n" +
                "\n" +
                "Terms and Conditions of Service: Transporter\n" +
                "    • TDS will be taken care of by the company itself and thus Transporter will not deduct any TDS whatsoever while clearing the payments to TransFly Infotech Pvt Ltd (TDS Declaration has already been applied for with Govt of Odisha/India).\n" +
                "    • Payment to be cleared within 2-3 calendar days to TransFly Infotech Pvt Ltd once the manual Challan or electronically generated Challan/Invoice has been deposited/shared with the transporter physically or via any other electronic media such as email, whatsapp etc.\n" +
                "    • Commission to be cleared to company’s account by every Mon for previous week by the Transporter.\n" +
                "    • For late payments, penal charges might be applicable as per company policy which can change from time to time without prior notification.\n" +
                "    • TransFly Infotech Company holds the right to change any of the terms and conditions of service and use and any other policies without a prior notification.\n" +
                "\n" +
                "\n" +
                "Other General Terms and Conditions of Service:\n" +
                "    • Rates mentioned on the App are Rate per Metric Tonne in INR which are the market rates provided by the Transporter in a specific mine.\n" +
                "    • ETL is Expected Time of Loading (as detailed below)\n" +
                "    • Vehicle owner is someone who is mentioned on the Government issued vehicle registration certificate (RC) \n" +
                "    • Authorised Representative is the one who has been authorised by the vehicle owner through Power Of Attorney or any such government specified authorization document and acts in the capacity of Vehicle Owner.                                                       \n" +
                "    • The loading of vehicles is dependent on mines operations and thus out of control from TransFly Infotech Pvt Ltd point of view and thus users are advised to not expect any compensation or other equivalent benefits from Transfly Infotech Pvt Ltd\n" +
                "    • The expected time of loading (ETL) updated on Apps/Website of TransFly Infotech Pvt Ltd is mentioned basis the general traffic conditions experienced in the specific areas of mine/loading location and TransFly Logistics takes no guarantee on the actual ground conditions and delays in loading. Users are suggested to take their own ground assessment.\n" +
                "    • The payment clearance cycles are dependent on the payments received from other various transporters associated with TransFly Infotech Pvt Ltd and thus TransFly Infotech Pvt Ltd is only able to clear the payments to other dependent parties post having received these payments.\n" +
                "    • TransFly Infotech Pvt Ltd will not be concerned with any issues/complaints arising out of behavior of the vehicle drivers not provided by the TransFly Infotech Pvt Ltd itself, all such issues/complaints will be addressed by the vehicle owner himself/herself or the Authorised Representative.\n" +
                "    • There will be GPS and Road dependability for loading the vehicles, TransFly Infotech Pvt Ltd takes no guarantee on smooth functioning of these as these are out of scope of company’s control.\n" +
                "    • All the registrations will be reviewed by the TransFly Infotech Pvt Ltd backend team and duly approved after verifying details such as RC and other documents. TransFly Infotech Pvt Ltd reserves the right to approve or decline registration as they deem right and this can not be questioned.\n" +
                "    • The KYC images loaded at the time of log in by users will be used for identifying the authenticity of license/ownership of vehicle or transporter license and as per Government rules for payment clearance etc.\n" +
                "    • Any bookings done on app to load vehicle on a specific point or location if not loaded for 24 hours time will be cancelled by the backend team and user needs to do the booking again.\n" +
                "    • The daily rates for loading will be revised once provided from the concerned authorities to TransFly Infotech Pvt Ltd which would be updated by 8.30 AM everyday, if any changes from the previous day’s rates. These rates will remain applicable until next change provided on the Challan by the transporter.\n" +
                "    • If any confusion is there in daily rates, the same will be adjusted and paid as per the Challan provided by the transporter.\n" +
                "    • Rewards and Referral programs: Participating in Rewards and Referral programs are optional; users must exercise caution and discretion before participating in such programs.\n" +
                "    • Referral reward points would be given on first come first served basis for the same registration number (number plate) vehicle getting referred by two or more users.\n" +
                "    • TransFly Infotech Company holds the right to change any of the terms and conditions of service and use and any other policies without a prior notification.\n" +
                "    • All disputes would be subject to the exclusive jurisdiction of honorable Keonjhar Districts Courts (Keonjhar, Odisha) only.\n" +
                "\n" +
                "If anywhere, any user is not clear about any of mentioned terms and conditions above, we recommend to contact us via customer care, whatsapp, email etc to take full clarity and don’t blindly agree to any of these terms and conditions. \n" +
                "\n" +
                "TransFly Infotech Pvt Ltd ®\n" +
                "Keonjhar, Odisha (India) 758001");

    }
}