package org.mappingviolence.poi;

import java.util.Collection;

public class Person {
  private Collection<Identity> identities;

  public Person() {

  }

  public boolean addIdentity(Identity newIdentity) {
    return identities.add(newIdentity);
  }

  public boolean removeIdentity(Identity identity) {
    return identities.remove(identity);
  }

  public void setIdentities(Collection<Identity> identities) {
    this.identities = identities;
  }

  public Collection<Identity> getIdentities() {
    return identities;
  }
}
