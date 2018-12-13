package NumConAxis2Client;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.dataaccess.www.webservicesserver.*;
import com.dataaccess.www.webservicesserver.NumberConversionStub.NumberToDollars;
import com.dataaccess.www.webservicesserver.NumberConversionStub.NumberToDollarsResponse;

public class NumConAxis2Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		try {
			
			BigDecimal randNum = new BigDecimal("20");
			NumberConversionStub NumConStub = new NumberConversionStub ();
			
			NumberToDollars ND = new NumberToDollars();
			ND.setDNum(randNum);
			NumberToDollarsResponse res = NumConStub.numberToDollars(ND);
			System.out.println(res.getNumberToDollarsResult());
			
		}catch(AxisFault e)
		{
			
		}catch(RemoteException e)
		{
			
		}
		
		

	}

}
