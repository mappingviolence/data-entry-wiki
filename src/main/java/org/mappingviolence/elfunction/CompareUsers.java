package org.mappingviolence.elfunction;

import org.mappingviolence.database.User;

public class CompareUsers {
  public static boolean compareUsers(User u1, User u2) {
    return u1.getRole().ordinal() >= u2.getRole().ordinal();
  }
}
