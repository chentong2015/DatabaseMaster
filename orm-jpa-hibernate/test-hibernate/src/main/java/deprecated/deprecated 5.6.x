从低版本升级到5.6.x版本之后，以下的一些使用应该被废弃
1.  WARN - HHH90000012: Recognized obsolete hibernate namespace http://hibernate.sourceforge.net/hibernate-mapping.
    Use namespace http://www.hibernate.org/dtd/hibernate-mapping instead.
    Support for obsolete DTD/XSD namespaces may be removed at any time.

2. WARN - HHH90000009: The outer-join attribute on <many-to-many> has been deprecated.
   Instead of outer-join="false", use lazy="extra" with <map>, <set>, <bag>, <idbag>, or <list>, which will only initialize entities (not as a proxy) as needed.

3. WARN - HHH000305: Could not create proxy factory for:xx.stp.workflow.config.Interface
   org.hibernate.HibernateException: Getter methods of lazy classes cannot be final: xx.stp.workflow.config.Interface#getId
   at org.hibernate.proxy.pojo.ProxyFactoryHelper.validateGetterSetterMethodProxyability(ProxyFactoryHelper.java:96)
   at org.hibernate.tuple.entity.PojoEntityTuplizer.buildProxyFactory(PojoEntityTuplizer.java:97)
   at org.hibernate.tuple.entity.AbstractEntityTuplizer.<init>(AbstractEntityTuplizer.java:161)
   at org.hibernate.tuple.entity.PojoEntityTuplizer.<init>(PojoEntityTuplizer.java:53)
   ...

4. WARN - HHH90000025: Encountered multiple component mappings for the same java class [xx.FieldFormat] with different property mappings.
   This is deprecated and will be removed in a future version. Every property mapping combination should have its own java class
    <component name="currency1PerCurrency2Conventions" class="xx.statics.fx.Currency1PerCurrency2Conventions">
      <component name="fieldFormat" class="xx.statics.fx.FieldFormat">
        <property type="java.lang.Integer" name="fieldSize" column="M_SPOT_FT_S0"/>
        <property type="java.lang.Integer" name="fieldDecimals" column="M_SPOT_FT_D0"/>
        <property type="java.lang.Double" name="formFactor" column="M_SPOT_FF0"/>
      </component>
    </component>
    <component name="currency2PerCurrency1Conventions" class="xx.statics.fx.Currency2PerCurrency1Conventions">
      <component name="fieldFormat" class="xx.statics.fx.FieldFormat">
        <property type="java.lang.Integer" name="fieldSize" column="M_SPOT_FT_S1"/>
        <property type="java.lang.Integer" name="fieldDecimals" column="M_SPOT_FT_D1"/>
        <property type="java.lang.Double" name="formFactor" column="M_SPOT_FF1"/>
      </component>
    </component>

   
   