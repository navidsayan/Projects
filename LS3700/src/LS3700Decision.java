package com.td.rcs.ivr.customerservice.decision;

import com.audium.server.AudiumException;
import com.audium.server.proxy.DecisionConfigInterface;
import com.audium.server.session.ElementAPI;
import com.audium.server.xml.DecisionElementConfig;
import com.cvg.cisco.reusable.components.Log;
import com.cvg.cisco.reusable.components.Utilities;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import com.cvg.cisco.reusable.components.FrameworkBase;
import static com.cvg.cisco.reusable.components.GeneralConstants.STATE_NAME;
import static com.cvg.cisco.reusable.components.GeneralConstants.STATE_TYPE;
import static com.cvg.cisco.reusable.components.GeneralConstants.STATE_ID;
import com.audium.server.xml.ElementConfig;
import com.audium.server.voiceElement.Setting;
import com.td.rcs.ivr.customerservice.general.CustServUtils;
import com.td.rcs.ivr.customerservice.general.ReportingElements;
import com.td.rcs.ivr.customerservice.general.AddedTasks;

@SuppressWarnings("all")

/**<b>Description : </b>
* <br>This class represents the decision state in the call flow design.
* <br>It covers all the condition statements and logic to transition to next state within those conditions.
* <br>Any action that needs to be performed for each one of those conditions is also coded in this class.
* <br>These actions are basically calls to set methods of call flow request handler class.
*/
public class LS3700Decision extends FrameworkBase implements DecisionConfigInterface
{
	/**
	 * All Dynamic Decision Element Configuration classes must implement this method. This
	 * method will be called when the call flow reaches a Decision Element Configuration that
	 * references this class.
	 * @see com.audium.server.proxy.DecisionConfigInterface#getConfig(
	 * 		java.lang.String, com.audium.server.session.ElementAPI,
	 * 		com.audium.server.xml.DecisionElementConfig)
	 */
	@Override
	public DecisionElementConfig getConfig(
	    String name,
	    ElementAPI elementAPI,
	    DecisionElementConfig decisionElementConfig) throws AudiumException
	{
		String nextState;
		Map<String,String> stateMap;

		if (decisionElementConfig == null)
		{
			decisionElementConfig = new DecisionElementConfig();
		}

		try
		{
			com.td.rcs.ivr.customerservice.handlers.CallFlowRequestHandler callFlowReqHandler =
			    (com.td.rcs.ivr.customerservice.handlers.CallFlowRequestHandler)Utilities.getSessionVar(elementAPI, "_CVP.CallFlowRequestHandler");
			nextState = "XF9000";
			stateMap = (HashMap<String,String>)elementAPI.getApplicationAPI().getApplicationData("_CVP.StateMap");
			XMLGregorianCalendar lastMaintDate = callFlowReqHandler.getAcctInquiryResponse().getNameAddressDateLastMaintenance();
			Calendar calendar = Calendar.getInstance();
			
			Log.log("*** getCalendar: system COA ***** " + calendar.getTime(), elementAPI);
			Log.log("*** getlastMaintDate COA ***** " + lastMaintDate, elementAPI);
	



			if(callFlowReqHandler.getHostError())
			{
				callFlowReqHandler.setTransferReason("TechnicalDifficulties");
				callFlowReqHandler.setTechnicalDifficultyMessaging("LostStolen");
				ReportingElements.ls3700Condition1(elementAPI,callFlowReqHandler);
				AddedTasks.ls3700Condition1(elementAPI,callFlowReqHandler);
				nextState = "XF9000";
			}
			else if (CustServUtils.withInXDaysCOA(lastMaintDate, true, 30, elementAPI) && callFlowReqHandler.getCardReissue()) 
			{
				Log.log("*** inner withInXDays true 30 COA ***** " + (CustServUtils.withInXDaysCOA(lastMaintDate, true, 30, elementAPI)), elementAPI);

				callFlowReqHandler.setTransferReason("ENCOA");
				callFlowReqHandler.setCoaGen(true);
				AddedTasks.ls3700Condition2(elementAPI,callFlowReqHandler);
				nextState = "XF9000";
			}
			
			else
			{
				AddedTasks.ls3700Condition2(elementAPI,callFlowReqHandler);
				nextState = "LS3750";
			}

			ReportingElements.ls3700Always(elementAPI,callFlowReqHandler);
			Utilities.setSessionVar(elementAPI,"_CVP.PreviousState","LS3700");
			nextState = getNextState(elementAPI,nextState);
			Utilities.setSessionVar(elementAPI,"_CVP.NextState",nextState);
			Utilities.setSessionVar(elementAPI,"_CVP.NextStateType",stateMap.get(nextState));

			// DO NOT CHANGE THE BELOW LINES OF CODE.
			{
				String stateName = (String)Utilities.getSessionVar(elementAPI, "_CVP.CurrentState");
				String stateType = ((HashMap<String,String>)elementAPI.
				                    getApplicationAPI().getApplicationData("_CVP.StateMap")).get(stateName);
				// Set the State ID
				decisionElementConfig.setElementData(STATE_ID,stateName,Setting.STRING,true,ElementConfig.AFTER_EXITING_ELEMENT);
				Log.log(STATE_NAME, stateName, elementAPI);
				Log.log(STATE_TYPE, stateType, elementAPI);
			}

		}
		catch (Exception exception)
		{
			Log.logError("Trouble in LS3700Decision : ", exception ,elementAPI);
			Utilities.setApplicationError(elementAPI,true);
			throw new AudiumException(exception);
		}

		// Return decision element configuration
		return decisionElementConfig;
	}
}
