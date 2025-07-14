import 'package:flutter/material.dart';

class FlexDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Widget c1 = Container(
      width: 80.0,
      height: 150.0,
      color: Colors.yellow,
      child: Center(
        child: Text(
          'container1',
          style: TextStyle(color: Colors.white),
        ),
      ),
    );
    Widget c2 = Container(
      width: 100.0,
      height: 100.0,
      color: Colors.cyanAccent,
      child: Center(
        child: Text(
          'container2',
          style: TextStyle(color: Colors.white),
        ),
      ),
    );
    Widget c3 = Container(
      width: 160.0,
      height: 100.0,
      color: Colors.pinkAccent,
      child: Center(
        child: Text(
          'container3',
          style: TextStyle(color: Colors.white),
        ),
      ),
    );
    Widget flexForRow = Flex(
      direction: Axis.horizontal,
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisSize: MainAxisSize.max,
      textDirection: TextDirection.ltr,
      verticalDirection: VerticalDirection.down,
      textBaseline: TextBaseline.ideographic,
      children: <Widget>[c1, c2, c3],
    );
    Widget flexForColumn = Flex(
      direction: Axis.vertical,
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      crossAxisAlignment: CrossAxisAlignment.center,
      mainAxisSize: MainAxisSize.max,
      textDirection: TextDirection.ltr,
      verticalDirection: VerticalDirection.down,
      children: <Widget>[c1, c2, c3],
    );
    // TODO: implement build
    return flexForRow;
  }
}
