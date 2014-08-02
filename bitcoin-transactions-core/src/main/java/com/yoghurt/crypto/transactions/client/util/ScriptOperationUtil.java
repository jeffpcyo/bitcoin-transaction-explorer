package com.yoghurt.crypto.transactions.client.util;

import com.yoghurt.crypto.transactions.client.domain.transaction.script.Operation;
import com.yoghurt.crypto.transactions.client.domain.transaction.script.ScriptPart;

public final class ScriptOperationUtil {

  private ScriptOperationUtil() {}

  // TODO Get these out of some cache
  public static Operation getOperation(final int opcode) {
    // Test for simple push data ops
    if(opcode > 0 && 75 >= opcode) {
      return Operation.OP_PUSHDATA;
    }

    // Iterate over all operations and give back a match
    for(final Operation op : Operation.values()) {
      if(op.getOpcode() == opcode) {
        return op;
      }
    }

    // Can't find it, return invalid op
    return Operation.OP_INVALIDOPCODE;
  }

  /**
   * Data push operations are 1-75 or 82-96.
   */
  public static boolean isDataPushOperation(final Operation op) {
    return op == Operation.OP_PUSHDATA || isDataPushOperation(op.getOpcode());
  }

  public static boolean isDataPushOperation(final int opcode) {
    return opcode > 0 && 78 >= opcode || opcode > 82 && 96 >= opcode;
  }

  public static byte getOperationOpCode(final ScriptPart part) {
    if(part.getOperation() == Operation.OP_PUSHDATA) {
      return (byte) (part.getBytes().length & 0xFF);
    }

    return (byte) (part.getOperation().getOpcode() & 0xFF);
  }
}