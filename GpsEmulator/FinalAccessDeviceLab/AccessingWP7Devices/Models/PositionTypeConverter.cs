using System;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using System.ComponentModel;
using System.Device.Location;

namespace AccessingWP7Devices.Models
{
    public class PositionTypeConverter : TypeConverter
    {
        public override bool CanConvertFrom(ITypeDescriptorContext context, Type sourceType)
        {
            return typeof(string).Equals(sourceType);
        }

        public override object ConvertFrom(ITypeDescriptorContext context, System.Globalization.CultureInfo culture, object value)
        {
            string stringLocation = (string)value;
            string[] stringCoordinates = stringLocation.Split(',');

            return new GeoCoordinate(Convert.ToDouble(stringCoordinates[0]), Convert.ToDouble(stringCoordinates[1]));
        }
    }
}
