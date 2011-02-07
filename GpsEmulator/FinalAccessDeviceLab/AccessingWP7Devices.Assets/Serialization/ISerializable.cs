using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace AccessingWP7Devices.Assets.Serialization
{
    public interface ISerializable
    {
        void Serialize(BinaryWriter writer);
        void Deserialize(BinaryReader reader);
    }
}
