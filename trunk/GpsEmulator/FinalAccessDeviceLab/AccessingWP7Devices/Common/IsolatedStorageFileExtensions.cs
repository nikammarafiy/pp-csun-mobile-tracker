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
using System.IO.IsolatedStorage;

namespace AccessingWP7Devices.Common
{
    public static class IsolatedStorageFileExtensions
    {
        /// <summary>
        /// If directory doesn't exist, create it.
        /// </summary>
        /// <param name="directoryPath"></param>
        /// <param name="isoFile"></param>
        public static void EnsureDirectory(this IsolatedStorageFile isoFile, string directoryPath)
        {
            if (!isoFile.DirectoryExists(directoryPath))
            {
                isoFile.CreateDirectory(directoryPath);
            }
        }
    }
}
