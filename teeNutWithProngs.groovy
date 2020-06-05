import eu.mihosoft.vrl.v3d.parametrics.*;
CSG generate(){
	String type= "teeNutWithProngs"
	if(args==null)
		args=["M8"]
	// The variable that stores the current size of this vitamin
	StringParameter size = new StringParameter(	type+" Default",args.get(0),Vitamins.listVitaminSizes(type))
	HashMap<String,Object> measurments = Vitamins.getConfiguration( type,size.getStrValue())

	def barrelDiameterValue = measurments.barrelDiameter
	def flangeThicknessValue = measurments.flangeThickness
	def massCentroidXValue = measurments.massCentroidX
	def massCentroidYValue = measurments.massCentroidY
	def massCentroidZValue = measurments.massCentroidZ
	def massKgValue = measurments.massKg
	def priceValue = measurments.price
	def prongLengthValue = measurments.prongLength
	def sourceValue = measurments.source
	def totalHeightValue = measurments.totalHeight
for(String key:measurments.keySet().stream().sorted().collect(Collectors.toList()))
println "teeNutWithProngs value "+key+" "+measurments.get(key)
	// Stub of a CAD object
	CSG part = new Cube().toCSG()
	return part
		.setParameter(size)
		.setRegenerate({generate()})
}
return generate() 