package com.gate.mocktest.utils;

public final class BranchUtil {
    private BranchUtil() {}

    public static String normalize(String branch) {
        if (branch == null) return "CS";
        String b = branch.trim().toUpperCase();
        if (b.contains("COMPUTER SCIENCE") || b.equals("CSE") || b.equals("CS")) return "CS";
        if (b.contains("ELECTRONICS") || b.equals("ECE") || b.equals("EC")) return "EC";
        if (b.contains("ELECTRICAL") || b.equals("EEE") || b.equals("EE")) return "EE";
        if (b.contains("MECHANICAL") || b.equals("ME") || b.equals("MECH")) return "ME";
        if (b.contains("CIVIL") || b.equals("CE") || b.equals("CIVIL ENGINEERING")) return "CE";
        return b;
    }

    public static String display(String branch) {
        String b = normalize(branch);
        if ("CS".equals(b)) return "CSE";
        if ("EC".equals(b)) return "ECE";
        return b;
    }
}
