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
using Microsoft.Phone.Controls.Maps;
using Microsoft.Phone.Controls.Maps.Core;

namespace AccessingWP7Devices.Assets.Map
{    
    public static class MapHelpers
    {
        public static MapMode GetMode(DependencyObject obj)
        {
            return (MapMode)obj.GetValue(ModeProperty);
        }

        public static void SetMode(DependencyObject obj, MapMode value)
        {
            obj.SetValue(ModeProperty, value);
        }

        // Using a DependencyProperty as the backing store for Mode.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty ModeProperty =
            DependencyProperty.RegisterAttached(
                "Mode", typeof(MapMode),
                typeof(MapHelpers),
                new PropertyMetadata(MapMode.Road, MapModeChanged));

        private static void MapModeChanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            var map = d as Microsoft.Phone.Controls.Maps.Map;
            if (MapMode.Aerial.Equals(e.NewValue))
            {
                map.Mode = new AerialMode(true);
                return;
            }

            if (MapMode.Road.Equals(e.NewValue))
            {
                map.Mode = new RoadMode();
                return;
            }
        }
    }
}
