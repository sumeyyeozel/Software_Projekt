/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software_projekt;

/**
 *
 * @author nurda
 */
public enum MessageType {
    STRT, INIT, ENDINIT, PRETST, STRTPRE, ENDPRE, STRTPING, PING, STOPPING, SETTARGET, OPERTEMP, STOP, STRTBURNIN, BURNIN, UNDEF;
	public static MessageType getType(String name) {
		if (name.equalsIgnoreCase("STRT"))
			return STRT;
		if (name.equalsIgnoreCase("INIT"))
			return INIT;
		if (name.equalsIgnoreCase("ENDINIT"))
			return ENDINIT;
		if (name.equalsIgnoreCase("STRTPRE"))
			return STRTPRE;
		if (name.equalsIgnoreCase("PRETST"))
			return PRETST;
		if (name.equalsIgnoreCase("ENDPRE"))
			return ENDPRE;
		if (name.equalsIgnoreCase("STOP"))
			return STOP;
		if (name.equalsIgnoreCase("STRTBURNIN"))
			return STRTBURNIN;
		if (name.equalsIgnoreCase("BURNIN"))
			return BURNIN;
		if (name.equalsIgnoreCase("OPERTEMP"))
			return OPERTEMP;
		if (name.equalsIgnoreCase("SETTARGET"))
			return SETTARGET;
		if (name.equalsIgnoreCase("STRTPING"))
			return STRTPING;
		if (name.equalsIgnoreCase("PING"))
			return PING;
		if (name.equalsIgnoreCase("STOPPING"))
			return STOPPING;
		return UNDEF;
	}
}
