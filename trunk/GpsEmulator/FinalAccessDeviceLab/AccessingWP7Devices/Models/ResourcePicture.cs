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
using System.Runtime.Serialization;
using System.Windows.Media.Imaging;

namespace AccessingWP7Devices.Models
{
    [DataContract]
    public class ResourcePicture : Picture
    {
        [DataMember]
        public Uri ResourceUri { get; set; }

        public ResourcePicture()
        {
        }

        public ResourcePicture(Uri resourceUri)
        {
            ResourceUri = resourceUri;
        }

        protected override BitmapSource CreateBitmapSource()
        {
            return new BitmapImage(ResourceUri);
        }
    }
}
