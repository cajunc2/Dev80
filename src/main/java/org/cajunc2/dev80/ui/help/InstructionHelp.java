package org.cajunc2.dev80.ui.help;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cajunc2.dev80.ui.help.directives.DSDirectiveDetail;
import org.cajunc2.dev80.ui.help.directives.IncludeDirectiveDetail;
import org.cajunc2.dev80.ui.help.instructions.ADCInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.ADDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.ANDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.BITInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CALLInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CCFInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CPDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CPDRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CPIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CPIRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CPInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.CPLInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.DAAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.DECInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.DIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.DJNZInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.EIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.EXInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.EXXInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.HALTInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.IMInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.INCInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.INDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.INDRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.INIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.INIRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.INInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.InstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.JPInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.JRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.LDDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.LDDRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.LDIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.LDIRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.LDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.NEGInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.NOPInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.ORInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.OTDRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.OTIRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.OUTDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.OUTIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.OUTInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.POPInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.PUSHInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RESInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RETIInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RETInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RETNInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RLAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RLCAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RLCInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RLDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RLInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RRAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RRCAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RRCInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RRDInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RRInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.RSTInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SBCInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SCFInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SETInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SLAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SRAInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SRLInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.SUBInstructionDetail;
import org.cajunc2.dev80.ui.help.instructions.XORInstructionDetail;

public class InstructionHelp {
	private static final Logger logger = Logger.getLogger(InstructionHelp.class.getName());
	private final Map<String, InstructionDetail> map = new HashMap<>();

	public InstructionHelp() {
		add(new ADCInstructionDetail());
		add(new ADDInstructionDetail());
		add(new ANDInstructionDetail());
		add(new BITInstructionDetail());
		add(new CALLInstructionDetail());
		add(new CCFInstructionDetail());
		add(new CPDInstructionDetail());
		add(new CPDRInstructionDetail());
		add(new CPIInstructionDetail());
		add(new CPInstructionDetail());
		add(new CPIRInstructionDetail());
		add(new CPLInstructionDetail());
		add(new DAAInstructionDetail());
		add(new DECInstructionDetail());
		add(new DIInstructionDetail());
		add(new DJNZInstructionDetail());
		add(new EIInstructionDetail());
		add(new EXInstructionDetail());
		add(new EXXInstructionDetail());
		add(new HALTInstructionDetail());
		add(new IMInstructionDetail());
		add(new INCInstructionDetail());
		add(new INDInstructionDetail());
		add(new INDRInstructionDetail());
		add(new INIInstructionDetail());
		add(new INInstructionDetail());
		add(new INIRInstructionDetail());
		add(new JPInstructionDetail());
		add(new JRInstructionDetail());
		add(new LDDInstructionDetail());
		add(new LDDRInstructionDetail());
		add(new LDIInstructionDetail());
		add(new LDInstructionDetail());
		add(new LDIRInstructionDetail());
		add(new NEGInstructionDetail());
		add(new NOPInstructionDetail());
		add(new ORInstructionDetail());
		add(new OTDRInstructionDetail());
		add(new OTIRInstructionDetail());
		add(new OUTDInstructionDetail());
		add(new OUTIInstructionDetail());
		add(new OUTInstructionDetail());
		add(new POPInstructionDetail());
		add(new PUSHInstructionDetail());
		add(new RESInstructionDetail());
		add(new RETIInstructionDetail());
		add(new RETInstructionDetail());
		add(new RETNInstructionDetail());
		add(new RLAInstructionDetail());
		add(new RLCAInstructionDetail());
		add(new RLCInstructionDetail());
		add(new RLDInstructionDetail());
		add(new RLInstructionDetail());
		add(new RRAInstructionDetail());
		add(new RRCAInstructionDetail());
		add(new RRCInstructionDetail());
		add(new RRDInstructionDetail());
		add(new RRInstructionDetail());
		add(new RSTInstructionDetail());
		add(new SBCInstructionDetail());
		add(new SCFInstructionDetail());
		add(new SETInstructionDetail());
		add(new SLAInstructionDetail());
		add(new SRAInstructionDetail());
		add(new SRLInstructionDetail());
		add(new SUBInstructionDetail());
		add(new XORInstructionDetail());

		add(new IncludeDirectiveDetail());
		add(new DSDirectiveDetail());
	}

	public Optional<InstructionDetail> get(String mnemonic) {
		InstructionDetail id = map.get(mnemonic);
		return Optional.ofNullable(id);
	}

	private void add(InstructionDetail id) {
		String mnemonic = id.instructionName();
		if (mnemonic.isEmpty()) {
			logger.severe(id.getClass().getName() + " is missing an instruction name");
		}
		if (map.containsKey(mnemonic)) {
			logger.log(Level.WARNING, "Duplicate instruction name: " + mnemonic + " in these classes:");
			logger.log(Level.WARNING, id.getClass().getName());
			logger.log(Level.WARNING, map.get(mnemonic).getClass().getName());
		}
		map.put(mnemonic, id);
	}
}
