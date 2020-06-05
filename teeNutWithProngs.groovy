import java.util.stream.Collectors

import com.neuronrobotics.bowlerstudio.vitamins.Vitamins

import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Cube
import eu.mihosoft.vrl.v3d.Cylinder
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
	def flangeDiameterValue = measurments.flangeDiameter
	for(String key:measurments.keySet().stream().sorted().collect(Collectors.toList()))
		println "teeNutWithProngs value "+key+" "+measurments.get(key)
	CSG base = new Cylinder(flangeDiameterValue/2, flangeThicknessValue).toCSG()
	CSG barrel = new Cylinder(barrelDiameterValue/2, totalHeightValue).toCSG()
	double prongWidth = (flangeDiameterValue-barrelDiameterValue)/3.0
	CSG prong = new Cube(prongWidth,flangeThicknessValue,prongLengthValue)		
					.toCSG()
					.toXMax()
					.toYMax()
					.toZMin()
					.movex(flangeDiameterValue/2)
	def prongs=[]
	for(int i=0;i<360;i+=90)
		prongs.add(prong.rotz(i))				

	return CSG.unionAll([base,barrel,CSG.unionAll(prongs)])
		.setParameter(size)
		.setRegenerate({generate()})
}
return generate() 